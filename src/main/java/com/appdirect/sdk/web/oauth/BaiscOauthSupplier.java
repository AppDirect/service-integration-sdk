package com.appdirect.sdk.web.oauth;

import javax.servlet.Filter;

/**
 * Implementations of this interface provide a way for the service-integration-sdk
 * to retrieve the Basic filter. Each SDK client application must contain a bean
 * of this type in its application context in order for the communication with AppMarket to work.
 */

@FunctionalInterface
public interface BaiscOauthSupplier {
	/**
	 * Returns the Basic Filter
	 *
	 * @return the Filter to authorize incoming requests
	 */
	Filter getBasicFilter();
}
