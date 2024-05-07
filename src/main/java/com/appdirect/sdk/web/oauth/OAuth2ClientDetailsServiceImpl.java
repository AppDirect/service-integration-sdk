package com.appdirect.sdk.web.oauth;

import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import com.appdirect.sdk.appmarket.OAuth2CredentialsSupplier;

public class OAuth2ClientDetailsServiceImpl implements OAuth2ClientDetailsService {
    private final OAuth2CredentialsSupplier oAuth2CredentialsSupplier;

    OAuth2ClientDetailsServiceImpl(OAuth2CredentialsSupplier oAuth2CredentialsSupplier) {
        this.oAuth2CredentialsSupplier = oAuth2CredentialsSupplier;
    }

    @Override
    public OAuth2ProtectedResourceDetails getOAuth2ProtectedResourceDetails(String clientId) {
        return oAuth2CredentialsSupplier.getOAuth2ResourceDetails(clientId);
    }
}
