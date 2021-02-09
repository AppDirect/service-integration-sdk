package com.appdirect.sdk.credentials;

import java.util.function.Function;

import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import com.appdirect.sdk.appmarket.OAuth2CredentialsSupplier;


/**
 * Functionality to find OAuth2ProtectedResourceDetails by key from a different sources: Database, Monolith, etc.
 */
public class OAuth2BackendCredentialsSupplier implements OAuth2CredentialsSupplier {

	private final Function<String, OAuth2ProtectedResourceDetails> oAuth2CredentialsFinder;

	public OAuth2BackendCredentialsSupplier(Function<String, OAuth2ProtectedResourceDetails> credentialsFinder) {
		this.oAuth2CredentialsFinder = credentialsFinder;
	}

	@Override
	public OAuth2ProtectedResourceDetails getOAuth2ResourceDetails(String applicationUuid) {
		return oAuth2CredentialsFinder.apply(applicationUuid);
	}
}
