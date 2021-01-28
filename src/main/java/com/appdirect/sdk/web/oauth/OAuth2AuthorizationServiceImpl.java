package com.appdirect.sdk.web.oauth;

import javax.servlet.Filter;

public class OAuth2AuthorizationServiceImpl implements OAuth2AuthorizationService{
    private final OAuth2AuthorizationSupplier oAuth2AuthorizationSupplier;

    public OAuth2AuthorizationServiceImpl(OAuth2AuthorizationSupplier oAuth2AuthorizationSupplier) {
        this.oAuth2AuthorizationSupplier = oAuth2AuthorizationSupplier;
    }
    
    public Filter getOAuth2Filter() {
        return oAuth2AuthorizationSupplier.getOAuth2Filter();
    }
}
