package com.appdirect.sdk.web.oauth;

import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;

public class DeveloperSpecificAppmarketCredentialsConsumerDetailsService implements ConsumerDetailsService {
	private final DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;

	public DeveloperSpecificAppmarketCredentialsConsumerDetailsService(DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier) {
		this.credentialsSupplier = credentialsSupplier;
	}

	@Override
	public ConsumerDetails loadConsumerByConsumerKey(String consumerKey) {
		return consumerDetailsFrom(credentialsSupplier.getConsumerCredentials(consumerKey));
	}

	private ConsumerDetails consumerDetailsFrom(Credentials credentials) {
		return new ConnectorConsumerDetails(credentials.developerKey, credentials.developerSecret);
	}
}
