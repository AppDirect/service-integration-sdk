package com.appdirect.sdk.appmarket;

/**
 * Implementations of this interface provide a way for the service-integration-sdk
 * to retrieve the developer credentials. Each SDK client application must contain a bean
 * of this type in its application context in order for the communication with AppMarket to work.
 */
@FunctionalInterface
public interface DeveloperSpecificAppmarketCredentialsSupplier {

	/**
	 * Returns the credentials that applies to the given consumer key
	 *
	 * @param consumerKey the key used to make a request
	 * @return the full credentials object associated with this key or {@link Credentials#invalidCredentials()}
	 */
	Credentials getConsumerCredentials(String consumerKey);

}
