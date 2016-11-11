package com.appdirect.sdk.web.oauth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.oauth.provider.ConsumerDetails;

import com.appdirect.sdk.appmarket.Credentials;
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
	public void loadConsumerByConsumerKey_buildsConsumerDetails_withMatchingMainProductKey() throws Exception {
		DeveloperSpecificAppmarketCredentials credentials = new DeveloperSpecificAppmarketCredentials(someCredentials("zebra key", "s11"));
		when(credentialsSupplier.get()).thenReturn(credentials);

		ConsumerDetails consumerDetails = service.loadConsumerByConsumerKey("zebra key");

		assertThat(consumerDetails.getConsumerKey()).isEqualTo("zebra key");
		assertThat(consumerDetails.getSignatureSecret()).hasFieldOrPropertyWithValue("consumerSecret", "s11");
	}

	@Test
	public void loadConsumerByConsumerKey_buildsConsumerDetails_withMatchingAddonKey() throws Exception {
		DeveloperSpecificAppmarketCredentials credentials = new DeveloperSpecificAppmarketCredentials(someCredentials("red key", "s1"), someCredentials("blue key", "s2"));
		when(credentialsSupplier.get()).thenReturn(credentials);

		ConsumerDetails consumerDetails = service.loadConsumerByConsumerKey("blue key");

		assertThat(consumerDetails.getConsumerKey()).isEqualTo("blue key");
		assertThat(consumerDetails.getSignatureSecret()).hasFieldOrPropertyWithValue("consumerSecret", "s2");
	}

	@Test
	public void loadConsumerByConsumerKey_returnsEmptyConsumer_whenKeyIsNotFound() throws Exception {
		DeveloperSpecificAppmarketCredentials credentials = new DeveloperSpecificAppmarketCredentials(someCredentials("orange key", "s3"));
		when(credentialsSupplier.get()).thenReturn(credentials);

		ConsumerDetails consumerDetails = service.loadConsumerByConsumerKey("peach key");

		assertThat(consumerDetails.getConsumerKey()).isEqualTo("");
		assertThat(consumerDetails.getSignatureSecret()).hasFieldOrPropertyWithValue("consumerSecret", "");
	}

	private Credentials someCredentials(String key, String secret) {
		return new Credentials(key, secret);
	}
}
