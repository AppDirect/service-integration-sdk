package com.appdirect.sdk.web.oauth.spring.oauth1;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth.provider.ConsumerAuthentication;
import org.springframework.security.oauth.provider.token.OAuthAccessProviderToken;

/** @deprecated */
@Deprecated
public class DefaultAuthenticationHandler implements OAuthAuthenticationHandler {
    public DefaultAuthenticationHandler() {
    }

    public Authentication createAuthentication(HttpServletRequest request, ConsumerAuthentication authentication, OAuthAccessProviderToken authToken) {
        if (authToken != null) {
            Authentication userAuthentication = authToken.getUserAuthentication();
            if (userAuthentication instanceof AbstractAuthenticationToken) {
                ((AbstractAuthenticationToken)userAuthentication).setDetails(new OAuthAuthenticationDetails(request, authentication.getConsumerDetails()));
            }

            return userAuthentication;
        } else {
            return authentication;
        }
    }
}
