/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
