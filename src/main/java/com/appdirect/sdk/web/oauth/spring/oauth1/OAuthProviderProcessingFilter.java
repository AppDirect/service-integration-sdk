package com.appdirect.sdk.web.oauth.spring.oauth1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth.common.OAuthConsumerParameter;
import org.springframework.security.oauth.common.OAuthException;
import org.springframework.security.oauth.common.signature.CoreOAuthSignatureMethodFactory;
import org.springframework.security.oauth.common.signature.OAuthSignatureMethod;
import org.springframework.security.oauth.common.signature.OAuthSignatureMethodFactory;
import org.springframework.security.oauth.common.signature.SignatureSecret;
import org.springframework.security.oauth.common.signature.UnsupportedSignatureMethodException;
import org.springframework.security.oauth.provider.ConsumerAuthentication;
import org.springframework.security.oauth.provider.ConsumerCredentials;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.security.oauth.provider.InvalidOAuthParametersException;
import org.springframework.security.oauth.provider.OAuthVersionUnsupportedException;
import org.springframework.security.oauth.provider.nonce.ExpiringTimestampNonceServices;
import org.springframework.security.oauth.provider.nonce.OAuthNonceServices;
import org.springframework.security.oauth.provider.token.OAuthProviderToken;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;
import org.springframework.util.Assert;

/** @deprecated */
@Deprecated
public abstract class OAuthProviderProcessingFilter implements Filter, InitializingBean, MessageSourceAware {
    public static final String OAUTH_PROCESSING_HANDLED = "org.springframework.security.oauth.provider.OAuthProviderProcessingFilter#SKIP_PROCESSING";
    private final Log log = LogFactory.getLog(this.getClass());
    private final List<String> allowedMethods = new ArrayList(Arrays.asList("GET", "POST"));
    private OAuthProcessingFilterEntryPoint authenticationEntryPoint = new OAuthProcessingFilterEntryPoint();
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private String filterProcessesUrl = "/oauth_filter";
    private OAuthProviderSupport providerSupport = new CoreOAuthProviderSupport();
    private OAuthSignatureMethodFactory signatureMethodFactory = new CoreOAuthSignatureMethodFactory();
    private OAuthNonceServices nonceServices = new ExpiringTimestampNonceServices();
    private boolean ignoreMissingCredentials = false;
    private OAuthProviderTokenServices tokenServices;
    private ConsumerDetailsService consumerDetailsService;

    public OAuthProviderProcessingFilter() {
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.consumerDetailsService, "A consumer details service is required.");
        Assert.notNull(this.tokenServices, "Token services are required.");
    }

    public void init(FilterConfig ignored) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        if (this.skipProcessing(request)) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Processing explicitly skipped.");
            }

            chain.doFilter(servletRequest, servletResponse);
        } else if (this.requiresAuthentication(request, response, chain)) {
            if (!this.allowMethod(request.getMethod().toUpperCase())) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Method " + request.getMethod() + " not supported.");
                }

                response.sendError(405);
                return;
            }

            try {
                Map<String, String> oauthParams = this.getProviderSupport().parseParameters(request);
                if (!this.parametersAreAdequate(oauthParams)) {
                    if (!this.isIgnoreInadequateCredentials()) {
                        throw new InvalidOAuthParametersException(this.messages.getMessage("OAuthProcessingFilter.missingCredentials", "Inadequate OAuth consumer credentials."));
                    }

                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Supplied OAuth parameters are inadequate. Ignoring.");
                    }

                    chain.doFilter(request, response);
                } else {
                    String token;
                    if (this.log.isDebugEnabled()) {
                        StringBuilder builder = new StringBuilder("OAuth parameters parsed: ");
                        Iterator var8 = oauthParams.keySet().iterator();

                        while(var8.hasNext()) {
                            token = (String)var8.next();
                            builder.append(token).append('=').append((String)oauthParams.get(token)).append(' ');
                        }
                    }

                    String consumerKey = (String)oauthParams.get(OAuthConsumerParameter.oauth_consumer_key.toString());
                    if (consumerKey == null) {
                        throw new InvalidOAuthParametersException(this.messages.getMessage("OAuthProcessingFilter.missingConsumerKey", "Missing consumer key."));
                    }

                    ConsumerDetails consumerDetails = this.getConsumerDetailsService().loadConsumerByConsumerKey(consumerKey);

                    this.validateOAuthParams(consumerDetails, oauthParams);
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Parameters validated.");
                    }

                    token = (String)oauthParams.get(OAuthConsumerParameter.oauth_token.toString());
                    String signatureMethod = (String)oauthParams.get(OAuthConsumerParameter.oauth_signature_method.toString());
                    String signature = (String)oauthParams.get(OAuthConsumerParameter.oauth_signature.toString());
                    String signatureBaseString = this.getProviderSupport().getSignatureBaseString(request);
                    ConsumerCredentials credentials = new ConsumerCredentials(consumerKey, signature, signatureMethod, signatureBaseString, token);
                    ConsumerAuthentication authentication = new ConsumerAuthentication(consumerDetails, credentials, oauthParams);
                    authentication.setDetails(this.createDetails(request, consumerDetails));
                    Authentication previousAuthentication = SecurityContextHolder.getContext().getAuthentication();

                    try {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        this.validateSignature(authentication);
                        authentication.setSignatureValidated(true);
                        request.setAttribute("org.springframework.security.oauth.provider.OAuthProviderProcessingFilter#SKIP_PROCESSING", Boolean.TRUE);
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Signature validated.");
                        }

                        this.onValidSignature(request, response, chain);
                    } finally {
                        this.resetPreviousAuthentication(previousAuthentication);
                    }
                }
            } catch (AuthenticationException var21) {
                this.fail(request, response, var21);
            } catch (ServletException var22) {
                if (!(var22.getRootCause() instanceof AuthenticationException)) {
                    throw var22;
                }

                this.fail(request, response, (AuthenticationException)var22.getRootCause());
            }
        } else {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Request does not require authentication.  OAuth processing skipped.");
            }

            chain.doFilter(servletRequest, servletResponse);
        }

    }

    protected boolean parametersAreAdequate(Map<String, String> oauthParams) {
        return oauthParams.containsKey(OAuthConsumerParameter.oauth_consumer_key.toString());
    }

    protected void resetPreviousAuthentication(Authentication previousAuthentication) {
        SecurityContextHolder.getContext().setAuthentication(previousAuthentication);
    }

    protected Object createDetails(HttpServletRequest request, ConsumerDetails consumerDetails) {
        return new OAuthAuthenticationDetails(request, consumerDetails);
    }

    protected boolean allowMethod(String method) {
        return this.allowedMethods.contains(method);
    }

    protected void validateSignature(ConsumerAuthentication authentication) throws AuthenticationException {
        SignatureSecret secret = authentication.getConsumerDetails().getSignatureSecret();
        String token = authentication.getConsumerCredentials().getToken();
        OAuthProviderToken authToken = null;
        if (token != null && !"".equals(token)) {
            authToken = this.getTokenServices().getToken(token);
        }

        String signatureMethod = authentication.getConsumerCredentials().getSignatureMethod();

        OAuthSignatureMethod method;
        try {
            method = this.getSignatureMethodFactory().getSignatureMethod(signatureMethod, secret, authToken != null ? authToken.getSecret() : null);
        } catch (UnsupportedSignatureMethodException var9) {
            throw new OAuthException(var9.getMessage(), var9);
        }

        String signatureBaseString = authentication.getConsumerCredentials().getSignatureBaseString();
        String signature = authentication.getConsumerCredentials().getSignature();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Verifying signature " + signature + " for signature base string " + signatureBaseString + " with method " + method.getName() + ".");
        }

        method.verify(signatureBaseString, signature);
    }

    protected abstract void onValidSignature(HttpServletRequest var1, HttpServletResponse var2, FilterChain var3) throws IOException, ServletException;

    protected void validateOAuthParams(ConsumerDetails consumerDetails, Map<String, String> oauthParams) throws InvalidOAuthParametersException {
        String version = (String)oauthParams.get(OAuthConsumerParameter.oauth_version.toString());
        if (version != null && !"1.0".equals(version)) {
            throw new OAuthVersionUnsupportedException("Unsupported OAuth version: " + version);
        } else {
            String realm = (String)oauthParams.get("realm");
            realm = realm != null && !"".equals(realm) ? realm : null;
            if (realm != null && !realm.equals(this.authenticationEntryPoint.getRealmName())) {
                throw new InvalidOAuthParametersException(this.messages.getMessage("OAuthProcessingFilter.incorrectRealm", new Object[]{realm, this.getAuthenticationEntryPoint().getRealmName()}, "Response realm name '{0}' does not match system realm name of '{1}'"));
            } else {
                String signatureMethod = (String)oauthParams.get(OAuthConsumerParameter.oauth_signature_method.toString());
                if (signatureMethod == null) {
                    throw new InvalidOAuthParametersException(this.messages.getMessage("OAuthProcessingFilter.missingSignatureMethod", "Missing signature method."));
                } else {
                    String signature = (String)oauthParams.get(OAuthConsumerParameter.oauth_signature.toString());
                    if (signature == null) {
                        throw new InvalidOAuthParametersException(this.messages.getMessage("OAuthProcessingFilter.missingSignature", "Missing signature."));
                    } else {
                        String timestamp = (String)oauthParams.get(OAuthConsumerParameter.oauth_timestamp.toString());
                        if (timestamp == null) {
                            throw new InvalidOAuthParametersException(this.messages.getMessage("OAuthProcessingFilter.missingTimestamp", "Missing timestamp."));
                        } else {
                            String nonce = (String)oauthParams.get(OAuthConsumerParameter.oauth_nonce.toString());
                            if (nonce == null) {
                                throw new InvalidOAuthParametersException(this.messages.getMessage("OAuthProcessingFilter.missingNonce", "Missing nonce."));
                            } else {
                                try {
                                    this.getNonceServices().validateNonce(consumerDetails, Long.parseLong(timestamp), nonce);
                                } catch (NumberFormatException var10) {
                                    throw new InvalidOAuthParametersException(this.messages.getMessage("OAuthProcessingFilter.invalidTimestamp", new Object[]{timestamp}, "Timestamp must be a positive integer. Invalid value: {0}"));
                                }

                                this.validateAdditionalParameters(consumerDetails, oauthParams);
                            }
                        }
                    }
                }
            }
        }
    }

    protected void validateAdditionalParameters(ConsumerDetails consumerDetails, Map<String, String> oauthParams) {
    }

    protected void onNewTimestamp() throws AuthenticationException {
        throw new InvalidOAuthParametersException(this.messages.getMessage("OAuthProcessingFilter.timestampNotNew", "A new timestamp should not be used in a request for an access token."));
    }

    protected void fail(HttpServletRequest request, HttpServletResponse response, AuthenticationException failure) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication((Authentication)null);
        if (this.log.isDebugEnabled()) {
            this.log.debug(failure);
        }

        this.authenticationEntryPoint.commence(request, response, failure);
    }

    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(59);
        if (pathParamIndex > 0) {
            uri = uri.substring(0, pathParamIndex);
        }

        if ("".equals(request.getContextPath())) {
            return uri.endsWith(this.filterProcessesUrl);
        } else {
            boolean match = uri.endsWith(request.getContextPath() + this.filterProcessesUrl);
            if (this.log.isDebugEnabled()) {
                this.log.debug(uri + (match ? " matches " : " does not match ") + this.filterProcessesUrl);
            }

            return match;
        }
    }

    protected boolean skipProcessing(HttpServletRequest request) {
        return request.getAttribute("org.springframework.security.oauth.provider.OAuthProviderProcessingFilter#SKIP_PROCESSING") != null && Boolean.TRUE.equals(request.getAttribute("org.springframework.security.oauth.provider.OAuthProviderProcessingFilter#SKIP_PROCESSING"));
    }

    public OAuthProcessingFilterEntryPoint getAuthenticationEntryPoint() {
        return this.authenticationEntryPoint;
    }

    @Autowired(
            required = false
    )
    public void setAuthenticationEntryPoint(OAuthProcessingFilterEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    public ConsumerDetailsService getConsumerDetailsService() {
        return this.consumerDetailsService;
    }

    @Autowired
    public void setConsumerDetailsService(ConsumerDetailsService consumerDetailsService) {
        this.consumerDetailsService = consumerDetailsService;
    }

    public OAuthNonceServices getNonceServices() {
        return this.nonceServices;
    }

    @Autowired(
            required = false
    )
    public void setNonceServices(OAuthNonceServices nonceServices) {
        this.nonceServices = nonceServices;
    }

    public OAuthProviderTokenServices getTokenServices() {
        return this.tokenServices;
    }

    @Autowired
    public void setTokenServices(OAuthProviderTokenServices tokenServices) {
        this.tokenServices = tokenServices;
    }

    public String getFilterProcessesUrl() {
        return this.filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public OAuthProviderSupport getProviderSupport() {
        return this.providerSupport;
    }

    @Autowired(
            required = false
    )
    public void setProviderSupport(OAuthProviderSupport providerSupport) {
        this.providerSupport = providerSupport;
    }

    public OAuthSignatureMethodFactory getSignatureMethodFactory() {
        return this.signatureMethodFactory;
    }

    @Autowired(
            required = false
    )
    public void setSignatureMethodFactory(OAuthSignatureMethodFactory signatureMethodFactory) {
        this.signatureMethodFactory = signatureMethodFactory;
    }

    public boolean isIgnoreInadequateCredentials() {
        return this.ignoreMissingCredentials;
    }

    public void setIgnoreMissingCredentials(boolean ignoreMissingCredentials) {
        this.ignoreMissingCredentials = ignoreMissingCredentials;
    }

    public void setAllowedMethods(List<String> allowedMethods) {
        this.allowedMethods.clear();
        if (allowedMethods != null) {
            Iterator var2 = allowedMethods.iterator();

            while(var2.hasNext()) {
                String allowedMethod = (String)var2.next();
                this.allowedMethods.add(allowedMethod.toUpperCase());
            }
        }

    }
}
