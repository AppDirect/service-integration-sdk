package com.appdirect.web.config;

import javax.servlet.Filter;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth.common.OAuthException;
import org.springframework.security.oauth.common.signature.OAuthSignatureMethod;
import org.springframework.security.oauth.common.signature.SignatureSecret;
import org.springframework.security.oauth.common.signature.UnsupportedSignatureMethodException;
import org.springframework.security.oauth.provider.ConsumerAuthentication;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.token.OAuthProviderToken;

@Slf4j
@NoArgsConstructor
public class CustomProtectedResourceProcessingFilter extends ProtectedResourceProcessingFilter implements Filter {
    @Value("${signature.validation.use.https}")
    private Boolean signatureValidationUseHttps;

    @Override
    protected void validateSignature(ConsumerAuthentication authentication) throws AuthenticationException {
        SignatureSecret secret = authentication.getConsumerDetails().getSignatureSecret();
        String token = authentication.getConsumerCredentials().getToken();
        OAuthProviderToken authToken = null;
        if (StringUtils.isNotBlank(token)) {
            authToken = this.getTokenServices().getToken(token);
        }
        String signatureMethod = authentication.getConsumerCredentials().getSignatureMethod();
        OAuthSignatureMethod method;
        try {
            method = this.getSignatureMethodFactory().getSignatureMethod(signatureMethod, secret, authToken != null ? authToken.getSecret() : null);
        } catch (UnsupportedSignatureMethodException e) {
            throw new OAuthException(e.getMessage(), e);
        }

        String signature = authentication.getConsumerCredentials().getSignature();
        String signatureBaseString;
        if (Boolean.TRUE.equals(signatureValidationUseHttps)) {
            // Validate signature against an https since load balancers can remove https
            signatureBaseString = authentication.getConsumerCredentials().getSignatureBaseString().replaceFirst("https?", "https");
        } else {
            signatureBaseString = authentication.getConsumerCredentials().getSignatureBaseString();
        }
        method.verify(signatureBaseString, signature);
    }
}
