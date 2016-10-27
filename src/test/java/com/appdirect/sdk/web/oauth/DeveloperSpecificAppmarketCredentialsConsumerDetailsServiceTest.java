package com.appdirect.sdk.web.oauth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.oauth.provider.ConsumerDetails;

import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;

public class DeveloperSpecificAppmarketCredentialsConsumerDetailsServiceTest {

	private Supplier<DeveloperSpecificAppmarketCredentials> credentialsSupplier;
	private DeveloperSpecificAppmarketCredentialsConsumerDetailsService service;

	@Before
	public void setup() throws Exception {
		credentialsSupplier = mock(DeveloperSpecificAppmarketCredentialsSupplier.class);

		service = new DeveloperSpecificAppmarketCredentialsConsumerDetailsService(credentialsSupplier);
	}

	@Test
	public void loadConsumerByConsumerKey_buildsConsumerDetails_fromCredentials() throws Exception {
		DeveloperSpecificAppmarketCredentials credentials = new DeveloperSpecificAppmarketCredentials("some-key", "some-secret");
		when(credentialsSupplier.get()).thenReturn(credentials);

		ConsumerDetails consumerDetails = service.loadConsumerByConsumerKey("some-key");

		assertThat(consumerDetails.getConsumerKey()).isEqualTo("some-key");
		assertThat(consumerDetails.getSignatureSecret()).hasFieldOrPropertyWithValue("consumerSecret", "some-secret");
	}

	@Test
	public void loadConsumerByConsumerKey_ignores_consumerKey() throws Exception {
		DeveloperSpecificAppmarketCredentials credentials = new DeveloperSpecificAppmarketCredentials("some-key", "some-secret");
		when(credentialsSupplier.get()).thenReturn(credentials);

		ConsumerDetails consumerDetails = service.loadConsumerByConsumerKey("This value will be ignored");

		assertThat(consumerDetails.getConsumerKey()).isEqualTo("some-key");
	}
}
