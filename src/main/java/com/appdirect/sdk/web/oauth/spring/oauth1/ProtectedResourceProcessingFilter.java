package com.appdirect.sdk.web.oauth.spring.oauth1;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth.provider.ConsumerAuthentication;
import org.springframework.security.oauth.provider.ExtraTrustConsumerDetails;
import org.springframework.security.oauth.provider.InvalidOAuthParametersException;
import org.springframework.security.oauth.provider.token.OAuthAccessProviderToken;
import org.springframework.security.oauth.provider.token.OAuthProviderToken;
import org.springframework.util.StringUtils;

/** @deprecated */
@Deprecated
public class ProtectedResourceProcessingFilter extends OAuthProviderProcessingFilter {
    private boolean allowAllMethods = true;
    private OAuthAuthenticationHandler authHandler = new DefaultAuthenticationHandler();

    public ProtectedResourceProcessingFilter() {
        this.setIgnoreMissingCredentials(true);
    }

    protected boolean allowMethod(String method) {
        return this.allowAllMethods || super.allowMethod(method);
    }

    protected void onValidSignature(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        ConsumerAuthentication authentication = (ConsumerAuthentication)SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getConsumerCredentials().getToken();
        OAuthAccessProviderToken accessToken = null;
        if (StringUtils.hasText(token)) {
            OAuthProviderToken authToken = this.getTokenServices().getToken(token);
            if (authToken == null) {
                throw new AccessDeniedException("Invalid access token.");
            }

            if (!authToken.isAccessToken()) {
                throw new AccessDeniedException("Token should be an access token.");
            }

            if (authToken instanceof OAuthAccessProviderToken) {
                accessToken = (OAuthAccessProviderToken)authToken;
            }
        } else if (!(authentication.getConsumerDetails() instanceof ExtraTrustConsumerDetails) || ((ExtraTrustConsumerDetails)authentication.getConsumerDetails()).isRequiredToObtainAuthenticatedToken()) {
            throw new InvalidOAuthParametersException(this.messages.getMessage("ProtectedResourceProcessingFilter.missingToken", "Missing auth token."));
        }

        Authentication userAuthentication = this.authHandler.createAuthentication(request, authentication, accessToken);
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        chain.doFilter(request, response);
    }

    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        return true;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        throw new UnsupportedOperationException("The OAuth protected resource processing filter doesn't support a filter processes URL.");
    }

    public boolean isAllowAllMethods() {
        return this.allowAllMethods;
    }

    public void setAllowAllMethods(boolean allowAllMethods) {
        this.allowAllMethods = allowAllMethods;
    }

    public OAuthAuthenticationHandler getAuthHandler() {
        return this.authHandler;
    }

    public void setAuthHandler(OAuthAuthenticationHandler authHandler) {
        this.authHandler = authHandler;
    }
}
