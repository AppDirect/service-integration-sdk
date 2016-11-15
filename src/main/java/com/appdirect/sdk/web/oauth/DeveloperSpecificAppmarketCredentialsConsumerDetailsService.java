package com.appdirect.sdk.web.oauth;

import java.util.function.Function;

import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;

import com.appdirect.sdk.appmarket.Credentials;

public class DeveloperSpecificAppmarketCredentialsConsumerDetailsService implements ConsumerDetailsService {
	private final Function<String, Credentials> credentialsSupplier;

	public DeveloperSpecificAppmarketCredentialsConsumerDetailsService(Function<String, Credentials> credentialsSupplier) {
		this.credentialsSupplier = credentialsSupplier;
	}

	@Override
	public ConsumerDetails loadConsumerByConsumerKey(String consumerKey) {
		Credentials credentials = credentialsSupplier.apply(consumerKey);
		return consumerDetailsFrom(credentials == null ? emptyCredentials() : credentials);
	}

	private ConsumerDetails consumerDetailsFrom(Credentials credentials) {
		return new ConnectorConsumerDetails(credentials.developerKey, credentials.developerSecret);
	}

	private Credentials emptyCredentials() {
		return new Credentials("", "");
	}
}
