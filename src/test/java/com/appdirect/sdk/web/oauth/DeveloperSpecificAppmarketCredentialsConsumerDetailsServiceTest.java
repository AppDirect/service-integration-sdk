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
