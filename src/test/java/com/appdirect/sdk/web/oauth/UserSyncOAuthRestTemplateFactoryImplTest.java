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
 *
 */

package com.appdirect.sdk.web.oauth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.web.client.RestTemplate;

public class UserSyncOAuthRestTemplateFactoryImplTest {
	private UserSyncOAuthRestTemplateFactoryImpl userSyncRestTemplateFactory;

	@Before
	public void setUp() {
		userSyncRestTemplateFactory = new UserSyncOAuthRestTemplateFactoryImpl();
	}

	@Test
	public void testGetRestTemplate_ReturnsOauthTemplate() throws Exception {
		//When
		RestTemplate restTemplate = userSyncRestTemplateFactory.getRestTemplate("some-key", "some-secret");

		//Then
		assertThat(restTemplate).isInstanceOf(OAuthRestTemplate.class);
		OAuthRestTemplate oauthRestTemplate = (OAuthRestTemplate) restTemplate;

		ProtectedResourceDetails resource = oauthRestTemplate.getResource();
		assertThat(resource.getConsumerKey()).isEqualTo("some-key");
		assertThat(resource.getSharedSecret())
				.isInstanceOf(SharedConsumerSecretImpl.class)
				.hasFieldOrPropertyWithValue("consumerSecret", "some-secret");
	}
}
