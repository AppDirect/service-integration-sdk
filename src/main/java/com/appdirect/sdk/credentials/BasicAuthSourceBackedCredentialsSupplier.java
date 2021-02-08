package com.appdirect.sdk.credentials;

import java.util.function.Function;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;

/**
 * Functionality to find Credentials by key from a different sources: Database, ResourceFile, in memory cache, etc.
 */
public class BasicAuthSourceBackedCredentialsSupplier implements DeveloperSpecificAppmarketCredentialsSupplier {
	private final Function<String, Credentials> credentialsFinder;

	public BasicAuthSourceBackedCredentialsSupplier(Function<String, Credentials> credentialsFinder) {
		this.credentialsFinder = credentialsFinder;
	}

	@Override
	public Credentials getConsumerCredentials(String consumerKey) {
		return credentialsFinder.apply(consumerKey);
	}
}
