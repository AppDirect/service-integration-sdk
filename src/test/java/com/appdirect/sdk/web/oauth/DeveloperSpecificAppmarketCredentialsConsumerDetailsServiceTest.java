package com.appdirect.sdk.web.oauth;

import static com.appdirect.sdk.appmarket.Credentials.invalidCredentials;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.oauth.provider.ConsumerDetails;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;

public class DeveloperSpecificAppmarketCredentialsConsumerDetailsServiceTest {

	private DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;
	private DeveloperSpecificAppmarketCredentialsConsumerDetailsService service;

	@Before
	public void setup() throws Exception {
		credentialsSupplier = mock(DeveloperSpecificAppmarketCredentialsSupplier.class);

		service = new DeveloperSpecificAppmarketCredentialsConsumerDetailsService(credentialsSupplier);
	}

	@Test
	public void loadConsumerByConsumerKey_buildsConsumerDetails_passesKeyToCredentialsSupplier() throws Exception {
		when(credentialsSupplier.getConsumerCredentials("zebra key")).thenReturn(someCredentials("zebra key", "s11"));

		ConsumerDetails consumerDetails = service.loadConsumerByConsumerKey("zebra key");

		assertThat(consumerDetails.getConsumerKey()).isEqualTo("zebra key");
		assertThat(consumerDetails.getSignatureSecret()).hasFieldOrPropertyWithValue("consumerSecret", "s11");
	}

	@Test
	public void loadConsumerByConsumerKey_buildsConsumerDetails_supplierReturnsInvalidCredentials_andItsFine() throws Exception {
		when(credentialsSupplier.getConsumerCredentials("horse key")).thenReturn(invalidCredentials());

		ConsumerDetails consumerDetails = service.loadConsumerByConsumerKey("horse key");

		assertThat(consumerDetails.getConsumerKey()).isEqualTo("this key does not exist in the supplier");
		assertThat(consumerDetails.getSignatureSecret()).hasFieldOrPropertyWithValue("consumerSecret", "this key does not exist in the supplier");
	}

	private Credentials someCredentials(String key, String secret) {
		return new Credentials(key, secret);
	}
}
