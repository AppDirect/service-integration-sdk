package com.appdirect.sdk.web.oauth;

import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

public interface OAuth2ClientDetailsService {
    OAuth2ProtectedResourceDetails getOAuth2ProtectedResourceDetails(String clientId);
}
