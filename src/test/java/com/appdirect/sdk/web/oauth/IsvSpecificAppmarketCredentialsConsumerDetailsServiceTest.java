package com.appdirect.sdk.web.oauth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;

import org.springframework.security.oauth.provider.ConsumerDetails;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.appdirect.sdk.appmarket.IsvSpecificAppmarketCredentials;
import com.appdirect.sdk.appmarket.IsvSpecificAppmarketCredentialsSupplier;

public class IsvSpecificAppmarketCredentialsConsumerDetailsServiceTest {

	private Supplier<IsvSpecificAppmarketCredentials> credentialsSupplier;
	private IsvSpecificAppmarketCredentialsConsumerDetailsService service;

	@BeforeMethod
	public void setup() throws Exception {
		credentialsSupplier = mock(IsvSpecificAppmarketCredentialsSupplier.class);

		service = new IsvSpecificAppmarketCredentialsConsumerDetailsService(credentialsSupplier);
	}

	@Test
	public void loadConsumerByConsumerKey_buildsConsumerDetails_fromCredentials() throws Exception {
		IsvSpecificAppmarketCredentials credentials = new IsvSpecificAppmarketCredentials("some-key", "some-secret");
		when(credentialsSupplier.get()).thenReturn(credentials);

		ConsumerDetails consumerDetails = service.loadConsumerByConsumerKey("some-key");

		assertThat(consumerDetails.getConsumerKey()).isEqualTo("some-key");
		assertThat(consumerDetails.getSignatureSecret()).hasFieldOrPropertyWithValue("consumerSecret", "some-secret");
	}

	@Test
	public void loadConsumerByConsumerKey_ignores_consumerKey() throws Exception {
		IsvSpecificAppmarketCredentials credentials = new IsvSpecificAppmarketCredentials("some-key", "some-secret");
		when(credentialsSupplier.get()).thenReturn(credentials);

		ConsumerDetails consumerDetails = service.loadConsumerByConsumerKey("This value will be ignored");

		assertThat(consumerDetails.getConsumerKey()).isEqualTo("some-key");
	}
}
