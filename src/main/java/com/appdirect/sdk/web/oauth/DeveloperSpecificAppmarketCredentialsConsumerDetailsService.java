package com.appdirect.sdk.web.oauth;

import java.util.function.Supplier;

import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentials;

public class DeveloperSpecificAppmarketCredentialsConsumerDetailsService implements ConsumerDetailsService {
	private final Supplier<DeveloperSpecificAppmarketCredentials> credentialsSupplier;

	public DeveloperSpecificAppmarketCredentialsConsumerDetailsService(Supplier<DeveloperSpecificAppmarketCredentials> credentialsSupplier) {
		this.credentialsSupplier = credentialsSupplier;
	}

	@Override
	public ConsumerDetails loadConsumerByConsumerKey(String consumerKey) {
		DeveloperSpecificAppmarketCredentials credentials = credentialsSupplier.get();

		return consumerDetailsFrom(thePairMatching(consumerKey, credentials));
	}

	private ConsumerDetails consumerDetailsFrom(Credentials credentials) {
		return new ConnectorConsumerDetails(credentials.developerKey, credentials.developerSecret);
	}

	private Credentials thePairMatching(String keyToMatch, DeveloperSpecificAppmarketCredentials credentials) {
		if (credentials.getMainProductCredentials().developerKey.equals(keyToMatch)) {
			return credentials.getMainProductCredentials();
		}
		return credentials.getAddonCredentials().filter(c -> c.developerKey.equals(keyToMatch)).orElse(emptyCredentials());
	}

	private Credentials emptyCredentials() {
		return new Credentials("", "");
	}
}
