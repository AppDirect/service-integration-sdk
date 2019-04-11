package com.appdirect.sdk.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.collections.Maps;

import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;

public class OAuthCredentialsConfigurationTest {
	private static final String LEGACY_CREDENTIALS = "legacyKey:legacySecret";
	private static final String CREDENTIALS= "key:secret";

	@Mock
	private ConnectorOAuthConfigurationProperties properties;
	private OAuthCredentialsConfiguration oAuthCredentialsConfiguration;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		oAuthCredentialsConfiguration = new OAuthCredentialsConfiguration(properties);
	}

	@Test
	public void testSupplierCreation_withOnlyLegacyCreds() {
		when(properties.getLegacyCredentials()).thenReturn(Optional.of(LEGACY_CREDENTIALS));
		when(properties.getCredentials()).thenReturn(Maps.newHashMap());

		DeveloperSpecificAppmarketCredentialsSupplier supplier = oAuthCredentialsConfiguration.environmentCredentialsSupplier();

		assertThat(supplier).isNotNull();
		assertThat(supplier.getConsumerCredentials("legacyKey").getDeveloperSecret()).isEqualTo("legacySecret");
	}

	@Test
	public void testSupplierCreation_withoutLegacyCreds() {
		Map<String, String> credentialsMap = Maps.newHashMap();
		credentialsMap.put("testCredential", CREDENTIALS);

		when(properties.getLegacyCredentials()).thenReturn(Optional.empty());
		when(properties.getCredentials()).thenReturn(credentialsMap);

		DeveloperSpecificAppmarketCredentialsSupplier supplier = oAuthCredentialsConfiguration.environmentCredentialsSupplier();

		assertThat(supplier).isNotNull();
		assertThat(supplier.getConsumerCredentials("key").getDeveloperSecret()).isEqualTo("secret");
	}

	@Test
	public void testSupplierCreation_withBothCreds() {
		Map<String, String> credentialsMap = Maps.newHashMap();
		credentialsMap.put("testCredential", CREDENTIALS);

		when(properties.getLegacyCredentials()).thenReturn(Optional.of(LEGACY_CREDENTIALS));
		when(properties.getCredentials()).thenReturn(credentialsMap);

		DeveloperSpecificAppmarketCredentialsSupplier supplier = oAuthCredentialsConfiguration.environmentCredentialsSupplier();

		assertThat(supplier).isNotNull();
		assertThat(supplier.getConsumerCredentials("key").getDeveloperSecret()).isEqualTo("secret");
		assertThat(supplier.getConsumerCredentials("legacyKey").getDeveloperSecret()).isEqualTo("legacySecret");
	}
}
