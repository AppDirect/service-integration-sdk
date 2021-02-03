package com.appdirect.sdk.web.oauth;

public class OAuth2FeatureFlagServiceImpl implements OAuth2FeatureFlagService{
    private final OAuth2FeatureFlagSupplier oAuth2FeatureFlagSupplier;

    public OAuth2FeatureFlagServiceImpl(OAuth2FeatureFlagSupplier oAuth2FeatureFlagSupplier) {
        this.oAuth2FeatureFlagSupplier = oAuth2FeatureFlagSupplier;
    }

    public boolean isOAuth2Enabled() {
        return oAuth2FeatureFlagSupplier.isOAuth2Enabled();
    }
}
