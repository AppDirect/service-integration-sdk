package com.appdirect.sdk.web.oauth;

import jakarta.servlet.Filter;

/**
 * Implementations of this interface provide a way for the service-integration-sdk
 * to retrieve the Basic filter. Each SDK client application must contain a bean
 * of this type in its application context in order for the communication with AppMarket to work.
 */

@FunctionalInterface
public interface BasicAuthSupplier {
	/**
	 * Returns the Basic Filter
	 *
	 * @return the Filter to authorize incoming requests
	 */
	Filter getBasicAuthFilter();
}
