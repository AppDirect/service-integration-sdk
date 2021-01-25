package com.appdirect.sdk.web.oauth;

/**
 * Implementations of this interface provide a way for the service-integration-sdk
 * to check if the oAuth2 authorization is enabled. Each SDK client application must contain a bean
 * of this type in its application context in order for the communication with AppMarket to work.
 */
@FunctionalInterface
public interface DeveloperSpecificOAuth2FeatureFlagSupplier {
    boolean isOAuth2Enabled();
}
