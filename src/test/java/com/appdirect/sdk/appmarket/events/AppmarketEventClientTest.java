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

package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.APIResult.success;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.saml.ServiceProviderInformation;
import com.appdirect.sdk.web.oauth.RestTemplateFactory;


public class AppmarketEventClientTest {
	private RestTemplateFactory restTemplateFactory;
	private RestTemplate restOperations;
	private DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;

	private AppmarketEventClient testedFetcher;

	@Before
	public void setUp() throws Exception {
		restOperations = mock(RestTemplate.class);
		credentialsSupplier = mock(DeveloperSpecificAppmarketCredentialsSupplier.class);
		restTemplateFactory = mock(RestTemplateFactory.class);
		testedFetcher = new AppmarketEventClient(restTemplateFactory, credentialsSupplier);

		when(credentialsSupplier.getConsumerCredentials("some-key")).thenReturn(new Credentials("some-key", "some-secret"));
	}

	@Test
	public void fetchEvent_callsGet_onTheRightUrl_andSetsEventId() throws Exception {
		//Given
		String testUrl = "some.com/events/the-id-inferred-from-url";
		String testKey = "testKey";
		String testSecret = "testSecret";
		EventInfo testEventInfo = EventInfo.builder().build();

		when(restTemplateFactory.getOAuthRestTemplate(testKey, testSecret))
				.thenReturn(restOperations);
		when(restOperations.getForObject(testUrl, EventInfo.class))
				.thenReturn(testEventInfo);
		String testDeveloperKey = "testKey";
		String testDeveloperSecret = "testSecret";
		Credentials testCredentials = new Credentials(testDeveloperKey, testDeveloperSecret);
		//When
		EventInfo fetchedEvent = testedFetcher.fetchEvent(testUrl, testCredentials);

		//Then
		assertThat(fetchedEvent).isEqualTo(testEventInfo);
		assertThat(fetchedEvent.getId()).isEqualTo("the-id-inferred-from-url");
	}

	@Test
	public void resolveEvent_callsPost_onTheRightUrl() throws Exception {
		when(restTemplateFactory.getOAuthRestTemplate("some-key", "some-secret")).thenReturn(restOperations);
		APIResult resultToSend = success("async is resolved");

		testedFetcher.resolve("http://base.com", "id-of-the-event", resultToSend, "some-key");

		verify(restOperations).postForObject("http://base.com/api/integration/v1/events/id-of-the-event/result", resultToSend, String.class);
	}

	@Test
	public void resolveSamlIdp() {
		// Given
		when(restTemplateFactory.getOAuthRestTemplate("some-key", "some-secret")).thenReturn(restOperations);

		// When
		testedFetcher.resolveSamlIdp("http://base.com/saml/18749910", "some-key");

		// Then
		verify(restOperations, times(1)).getForObject("http://base.com/saml/18749910", ServiceProviderInformation.class);
	}
}
