package com.appdirect.sdk.configuration;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.credentials.StringBackedCredentialsSupplier;

@Configuration
@EnableConfigurationProperties(ConnectorOAuthConfigurationProperties.class)
@RequiredArgsConstructor
public class OAuthCredentialsConfiguration {
	private static final String DELIMITER = ",";

	private final ConnectorOAuthConfigurationProperties connectorOAuthConfigurationProperties;

	@Bean
	public DeveloperSpecificAppmarketCredentialsSupplier environmentCredentialsSupplier() {
		String oAuthCredentials = String.join(DELIMITER, connectorOAuthConfigurationProperties.getCredentials().values());

		//Backward compatibility
		if (connectorOAuthConfigurationProperties.getLegacyCredentials().isPresent()) {
			String legacyCredentials = connectorOAuthConfigurationProperties.getLegacyCredentials().get();
			oAuthCredentials = oAuthCredentials.isEmpty() ?  legacyCredentials : String.join(DELIMITER, oAuthCredentials, legacyCredentials);
		}

		return new StringBackedCredentialsSupplier(oAuthCredentials);
	}
}
