package com.appdirect.sdk.web.oauth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;

import org.springframework.security.oauth.provider.ConsumerDetails;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.appdirect.sdk.marketplace.IsvSpecificMarketplaceCredentials;
import com.appdirect.sdk.marketplace.IsvSpecificMarketplaceCredentialsSupplier;

public class IsvSpecificMarketplaceCredentialsConsumerDetailsServiceTest {

	private Supplier<IsvSpecificMarketplaceCredentials> credentialsSupplier;
	private IsvSpecificMarketplaceCredentialsConsumerDetailsService service;

	@BeforeMethod
	public void setup() throws Exception {
		credentialsSupplier = mock(IsvSpecificMarketplaceCredentialsSupplier.class);

		service = new IsvSpecificMarketplaceCredentialsConsumerDetailsService(credentialsSupplier);
	}

	@Test
	public void loadConsumerByConsumerKey_buildsConsumerDetails_fromCredentials() throws Exception {
		IsvSpecificMarketplaceCredentials credentials = new IsvSpecificMarketplaceCredentials("some-key", "some-secret");
		when(credentialsSupplier.get()).thenReturn(credentials);

		ConsumerDetails consumerDetails = service.loadConsumerByConsumerKey("some-key");

		assertThat(consumerDetails.getConsumerKey()).isEqualTo("some-key");
		assertThat(consumerDetails.getSignatureSecret()).hasFieldOrPropertyWithValue("consumerSecret", "some-secret");
	}

	@Test
	public void loadConsumerByConsumerKey_ignores_consumerKey() throws Exception {
		IsvSpecificMarketplaceCredentials credentials = new IsvSpecificMarketplaceCredentials("some-key", "some-secret");
		when(credentialsSupplier.get()).thenReturn(credentials);

		ConsumerDetails consumerDetails = service.loadConsumerByConsumerKey("This value will be ignored");

		assertThat(consumerDetails.getConsumerKey()).isEqualTo("some-key");
	}
}
