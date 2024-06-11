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
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.web.client.RestTemplate;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.saml.ServiceProviderInformation;
import com.appdirect.sdk.web.oauth.OAuth2AuthorizationSupplier;
import com.appdirect.sdk.web.oauth.RestTemplateFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppmarketEventClientTest {
	private RestTemplateFactory restTemplateFactory;
	private RestTemplate restOperations;
	private DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;
	private OAuth2AuthorizationSupplier oAuth2AuthorizationSupplier;
	private AppmarketEventClient testedFetcher;
	private ObjectMapper jsonMapper = new ObjectMapper();
	private OAuth2RestTemplate oAuth2RestTemplate;

	@Before
	public void setUp() throws Exception {
		restOperations = mock(RestTemplate.class);
		credentialsSupplier = mock(DeveloperSpecificAppmarketCredentialsSupplier.class);
		oAuth2AuthorizationSupplier = mock(OAuth2AuthorizationSupplier.class);
		restTemplateFactory = mock(RestTemplateFactory.class);
		oAuth2RestTemplate = mock(OAuth2RestTemplate.class);

		testedFetcher = new AppmarketEventClient(restTemplateFactory, credentialsSupplier, jsonMapper);

		when(credentialsSupplier.getConsumerCredentials("some-key")).thenReturn(new Credentials("some-key", "some-secret"));
		when(oAuth2AuthorizationSupplier.getOAuth2Filter()).thenReturn(new OAuth2AuthenticationProcessingFilter());
	}

	@Test
	public void fetchEvent_callsGet_onTheRightUrl_andSetsEventId() throws Exception {
		//Given
		String testUrl = "some.com/events/the-id-inferred-from-url";
		String testKey = "testKey";
		String testSecret = "testSecret";
		EventInfo testEventInfo = EventInfo.builder().build();
		HttpEntity<String> expectedEntity = expectedGetEntity();
		when(
				restTemplateFactory.getOAuthRestTemplate(testKey, testSecret)
		).thenReturn(
				restOperations
		);
		when(
				restOperations.exchange(
						testUrl,
						HttpMethod.GET,
						expectedEntity,
						EventInfo.class
				)
		).thenReturn(
				new ResponseEntity<>(testEventInfo, HttpStatus.OK)
		);

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
	public void fetchEvents_calls_withOauth2Get_onTheRightUrl_andSetsEventId() throws Exception {
		//Given
		String testUrl = "some.com/events/the-id-inferred-from-url";
		EventInfo testEventInfo = EventInfo.builder().build();
		HttpEntity<String> expectedEntity = expectedGetEntity();
		when(
				restTemplateFactory.getOAuth2RestTemplate(any())
		).thenReturn(
				oAuth2RestTemplate
		);
		when(
				oAuth2RestTemplate.exchange(
						testUrl,
						HttpMethod.GET,
						expectedEntity,
						EventInfo.class
				)
		).thenReturn(
				new ResponseEntity<>(testEventInfo, HttpStatus.OK)
		);

		//When
		EventInfo fetchedEvent = testedFetcher.fetchEvent(testUrl, any(OAuth2ProtectedResourceDetails.class));

		//Then
		assertThat(fetchedEvent).isEqualTo(testEventInfo);
		assertThat(fetchedEvent.getId()).isEqualTo("the-id-inferred-from-url");
	}

	private HttpEntity<String> expectedGetEntity() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(singletonList(MediaType.APPLICATION_JSON));
		return new HttpEntity<>("", requestHeaders);
	}

	@Test
	public void resolveEvent_callsPost_onTheRightUrl() throws Exception {
		when(restTemplateFactory.getOAuthRestTemplate("some-key", "some-secret")).thenReturn(restOperations);
		APIResult expectedApiResult = success("async is resolved");

		final ArgumentCaptor<HttpEntity> httpEntityArgumentCaptor = ArgumentCaptor.forClass(HttpEntity.class);
		testedFetcher.resolve("http://base.com", "id-of-the-event", expectedApiResult, "some-key");

		verify(restOperations)
				.exchange(
						eq("http://base.com/api/integration/v1/events/id-of-the-event/result"),
						eq(HttpMethod.POST),
						httpEntityArgumentCaptor.capture(),
						eq(String.class)
				);

		final HttpEntity actualEntity = httpEntityArgumentCaptor.getValue();
		final APIResult actualApiResult = jsonMapper.readValue((String) actualEntity.getBody(), APIResult.class);

		assertThat(actualEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
		assertThat(actualApiResult.getMessage()).isEqualTo(expectedApiResult.getMessage());
		assertThat(actualApiResult.isSuccess()).isEqualTo(expectedApiResult.isSuccess());
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

	@Test
	public void resolveEvent_withOauth2callsPost_onTheRightUrl() throws Exception {
		when(restTemplateFactory.getOAuth2RestTemplate(any())).thenReturn(oAuth2RestTemplate);
		when(oAuth2RestTemplate.getForObject("http://base.com", ServiceProviderInformation.class)).thenReturn(ServiceProviderInformation.builder().build());
		APIResult expectedApiResult = success("async is resolved");

		final ArgumentCaptor<HttpEntity> httpEntityArgumentCaptor = ArgumentCaptor.forClass(HttpEntity.class);
		testedFetcher.resolve("http://base.com", "id-of-the-event", expectedApiResult, any(OAuth2ProtectedResourceDetails.class));

		verify(oAuth2RestTemplate)
				.exchange(
						eq("http://base.com/api/integration/v1/events/id-of-the-event/result"),
						eq(HttpMethod.POST),
						httpEntityArgumentCaptor.capture(),
						eq(String.class)
				);

		final HttpEntity actualEntity = httpEntityArgumentCaptor.getValue();
		final APIResult actualApiResult = jsonMapper.readValue((String) actualEntity.getBody(), APIResult.class);

		assertThat(actualEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
		assertThat(actualApiResult.getMessage()).isEqualTo(expectedApiResult.getMessage());
		assertThat(actualApiResult.isSuccess()).isEqualTo(expectedApiResult.isSuccess());
	}

	@Test
	public void resolveSamlIdp_withOauth2() {
		// Given
		when(restTemplateFactory.getOAuth2RestTemplate(any())).thenReturn(oAuth2RestTemplate);
		when(oAuth2RestTemplate.getForObject("http://base.com/saml/18749910", ServiceProviderInformation.class)).thenReturn(ServiceProviderInformation.builder().build());
		// When
		testedFetcher.resolveSamlIdp("http://base.com/saml/18749910", any(OAuth2ProtectedResourceDetails.class));

		// Then
		verify(oAuth2RestTemplate, times(1)).getForObject("http://base.com/saml/18749910", ServiceProviderInformation.class);
	}

}
