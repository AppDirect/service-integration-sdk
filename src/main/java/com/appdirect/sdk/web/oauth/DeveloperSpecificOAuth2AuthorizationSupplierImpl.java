package com.appdirect.sdk.web.oauth;

import javax.servlet.Filter;

public class DeveloperSpecificOAuth2AuthorizationSupplierImpl implements DeveloperSpecificOAuth2AuthorizationSupplier{
    private final Filter oAuth2Filter;

    public DeveloperSpecificOAuth2AuthorizationSupplierImpl(Filter oAuth2Filter) {
        this.oAuth2Filter = oAuth2Filter;
    }

    @Override
    public Filter getOAuth2Filter() {
        return oAuth2Filter;
    }
}
