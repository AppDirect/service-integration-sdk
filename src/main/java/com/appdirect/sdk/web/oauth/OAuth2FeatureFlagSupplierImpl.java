package com.appdirect.sdk.web.oauth;

public class OAuth2FeatureFlagSupplierImpl implements OAuth2FeatureFlagSupplier {
    private boolean isOAuth2Enabled;

    public OAuth2FeatureFlagSupplierImpl(boolean isOAuth2Enabled) {
        this.isOAuth2Enabled = isOAuth2Enabled;
    }

    @Override
    public boolean isOAuth2Enabled() {
        return false;
    }
}
