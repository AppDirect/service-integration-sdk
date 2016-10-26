package com.appdirect.sdk.web.oauth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;

public class ConnectorConsumerDetailsTest {

	private ConnectorConsumerDetails consumerDetails;

	@Before
	public void theConsumerDetails() {
		consumerDetails = new ConnectorConsumerDetails("some-key", "some-secret");
	}

	@Test
	public void haveKeyAndSecret() throws Exception {
		assertThat(consumerDetails.getConsumerKey()).isEqualTo("some-key");
		assertThat(consumerDetails.getSignatureSecret())
				.isInstanceOf(SharedConsumerSecretImpl.class)
				.hasFieldOrPropertyWithValue("consumerSecret", "some-secret");
	}

	@Test
	public void haveNoAuthorities() throws Exception {
		assertThat(consumerDetails.getAuthorities()).isEmpty();
	}

	@Test
	public void haveAnEmptyName() throws Exception {
		assertThat(consumerDetails.getConsumerName()).isEmpty();
	}

	@Test
	public void isNotRequiredToObtainAuthenticatedToken() throws Exception {
		assertThat(consumerDetails.isRequiredToObtainAuthenticatedToken()).isFalse();
	}
}
