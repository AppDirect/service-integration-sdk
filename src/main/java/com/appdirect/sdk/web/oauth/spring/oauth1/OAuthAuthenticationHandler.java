package com.appdirect.sdk.web.oauth.spring.oauth1;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth.provider.ConsumerAuthentication;
import org.springframework.security.oauth.provider.token.OAuthAccessProviderToken;

/** @deprecated */
@Deprecated
public interface OAuthAuthenticationHandler {
    Authentication createAuthentication(HttpServletRequest var1, ConsumerAuthentication var2, OAuthAccessProviderToken var3);
}
