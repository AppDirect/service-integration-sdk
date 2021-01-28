package com.appdirect.sdk.web.oauth;

import javax.servlet.Filter;

/**
 * Implementations of this interface provide a way for the service-integration-sdk
 * to retrieve the oAuth2 filter. Each SDK client application must contain a bean
 * of this type in its application context in order for the communication with AppMarket to work.
 */
@FunctionalInterface
public interface OAuth2AuthorizationSupplier {

    /**
     * Returns the oAuth2 Filter
     *
     * @return the Filter to authorize incoming requests
     */
    Filter getOAuth2Filter();
}
