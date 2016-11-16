package com.appdirect.sdk.web.oauth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.oauth.provider.ConsumerDetails;

import com.appdirect.sdk.appmarket.Credentials;

public class DeveloperSpecificAppmarketCredentialsConsumerDetailsServiceTest {

	@Mock
	private Function<String, Credentials> credentialsSupplier;
	private DeveloperSpecificAppmarketCredentialsConsumerDetailsService service;

	@Before
	public void setup() throws Exception {
		initMocks(this);

		service = new DeveloperSpecificAppmarketCredentialsConsumerDetailsService(credentialsSupplier);
	}

	@Test
	public void loadConsumerByConsumerKey_buildsConsumerDetails_passesKeyToCredentialsSupplier() throws Exception {
		when(credentialsSupplier.apply("zebra key")).thenReturn(someCredentials("zebra key", "s11"));

		ConsumerDetails consumerDetails = service.loadConsumerByConsumerKey("zebra key");

		assertThat(consumerDetails.getConsumerKey()).isEqualTo("zebra key");
		assertThat(consumerDetails.getSignatureSecret()).hasFieldOrPropertyWithValue("consumerSecret", "s11");
	}

	@Test
	public void loadConsumerByConsumerKey_returnsEmptyConsumer_whenKeyIsNotFound() throws Exception {
		when(credentialsSupplier.apply("peach key")).thenReturn(null);

		ConsumerDetails consumerDetails = service.loadConsumerByConsumerKey("peach key");

		assertThat(consumerDetails.getConsumerKey()).isEqualTo("");
		assertThat(consumerDetails.getSignatureSecret()).hasFieldOrPropertyWithValue("consumerSecret", "");
	}

	private Credentials someCredentials(String key, String secret) {
		return new Credentials(key, secret);
	}
}
