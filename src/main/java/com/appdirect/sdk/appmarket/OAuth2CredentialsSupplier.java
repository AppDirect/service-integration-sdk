package com.appdirect.sdk.appmarket;

import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

/**
 * Implementations of this interface provide a way for the service-integration-sdk
 * to retrieve the separate credentials ie OAuth2. Each SDK client application must contain a bean
 * of this type in its application context in order for the communication with AppMarket to work.
 */
@FunctionalInterface
public interface OAuth2CredentialsSupplier {

	/**
	 * Returns the OAuth2ProtectedResourceDetails that applies to the given applicationUuid key
	 *
	 * @param applicationUuid the key used to make a request
	 * @return the full OAuth2ProtectedResourceDetails object associated with this key
	 */
	OAuth2ProtectedResourceDetails getOAuth2ResourceDetails(String applicationUuid);

}
