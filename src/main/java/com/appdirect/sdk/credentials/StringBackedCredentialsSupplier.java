package com.appdirect.sdk.credentials;

import static com.appdirect.sdk.appmarket.Credentials.invalidCredentials;

import java.util.Map;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;

/**
 * Creates an in-memory cache of (developer key -> developer secret) pairs from a string of the following format:
 * 
 * devKey1:devPassword1,devKey2:devPassTwo
 * 
 */
public class StringBackedCredentialsSupplier implements DeveloperSpecificAppmarketCredentialsSupplier {
	private final Map<String, String> allowedCredentials;

	public StringBackedCredentialsSupplier(String rawAllowedCredentials) {
		this.allowedCredentials = MapBuilderUtils.fromCommaDelimitedKeyValuePairs(rawAllowedCredentials);
	}

	@Override
	public Credentials getConsumerCredentials(String consumerKey) {
		String secret = allowedCredentials.get(consumerKey);
		return secret == null ? invalidCredentials() : new Credentials(consumerKey, secret);
	}
}
