package com.appdirect.sdk.web.oauth;

import javax.servlet.Filter;

public class OAuth2AuthorizationSupplierImpl implements OAuth2AuthorizationSupplier {
    private final Filter oAuth2Filter;

    public OAuth2AuthorizationSupplierImpl(Filter oAuth2Filter) {
        this.oAuth2Filter = oAuth2Filter;
    }

    @Override
    public Filter getOAuth2Filter() {
        return oAuth2Filter;
    }
}
