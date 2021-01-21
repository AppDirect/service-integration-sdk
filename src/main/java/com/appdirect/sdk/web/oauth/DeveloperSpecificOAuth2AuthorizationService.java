package com.appdirect.sdk.web.oauth;

import javax.servlet.Filter;

public class DeveloperSpecificOAuth2AuthorizationService {
    private final DeveloperSpecificOAuth2AuthorizationSupplier oAuth2AuthorizationSupplier;

    public DeveloperSpecificOAuth2AuthorizationService(DeveloperSpecificOAuth2AuthorizationSupplier oAuth2AuthorizationSupplier) {
        this.oAuth2AuthorizationSupplier = oAuth2AuthorizationSupplier;
    }
    
    public Filter getOAuth2Filter() {
        return oAuth2AuthorizationSupplier.getOAuth2Filter();
    }
}
