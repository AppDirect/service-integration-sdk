package com.appdirect.sdk.web.oauth;

public class DeveloperSpecificOAuth2FeatureFlagService {
    private final DeveloperSpecificOAuth2FeatureFlagSupplier oAuth2FeatureFlagSupplier;

    public DeveloperSpecificOAuth2FeatureFlagService(DeveloperSpecificOAuth2FeatureFlagSupplier oAuth2FeatureFlagSupplier) {
        this.oAuth2FeatureFlagSupplier = oAuth2FeatureFlagSupplier;
    }

    public boolean isOAuth2Enabled() {
        return oAuth2FeatureFlagSupplier.isOAuth2Enabled();
    }
}
