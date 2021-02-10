package com.appdirect.sdk.credentials;

import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.BasicAuthCredentialsSupplier;

@Slf4j
/**
 * Functionality to find Credentials by key from a different sources: Database, ResourceFile, in memory cache, etc.
 */
public class BasicAuthSourceBackedCredentialsSupplier implements BasicAuthCredentialsSupplier {
	private final Function<String, Credentials> credentialsFinder;

	public BasicAuthSourceBackedCredentialsSupplier(Function<String, Credentials> credentialsFinder) {
		this.credentialsFinder = credentialsFinder;
	}

	@Override
	public Credentials getConsumerCredentials(String consumerKey) {
		log.info("User= {}", consumerKey);
		Credentials credentials = credentialsFinder.apply(consumerKey);
		return credentials;
	}
}
