package com.appdirect.sdk.web.oauth;

public class DeveloperSpecificOAuth2FeatureFlagSupplierImpl implements DeveloperSpecificOAuth2FeatureFlagSupplier{
    private boolean isOAuth2Enabled;

    public DeveloperSpecificOAuth2FeatureFlagSupplierImpl(boolean isOAuth2Enabled) {
        this.isOAuth2Enabled = isOAuth2Enabled;
    }

    @Override
    public boolean isOAuth2Enabled() {
        return false;
    }
}
