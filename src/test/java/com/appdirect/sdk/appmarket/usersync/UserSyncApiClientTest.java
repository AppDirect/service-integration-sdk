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

package com.appdirect.sdk.appmarket.usersync;

import static com.appdirect.sdk.support.ContentOf.resourceAsString;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.exactly;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.apache.http.client.utils.URIBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import com.appdirect.sdk.exception.UserSyncException;
import com.appdirect.sdk.exception.UserSyncTooManyRequestsException;
import com.appdirect.sdk.web.oauth.RestTemplateFactory;
import com.appdirect.sdk.web.oauth.UserSyncRestTemplateFactoryImpl;
import com.github.tomakehurst.wiremock.junit.WireMockRule;


public class UserSyncApiClientTest {
	private static final String OAUTH_KEY = "testKey";
	private static final String OAUTH_SECRET = "testSecret";
	private static final String USER_SYNC_ENDPOINT = "/api/sync/v1/tasks";
	private static final String HOST = "localhost";
	private static final String OUTBOUND_CLIENT_ID = "my-outbound-client-id";
	private static final String OUTBOUND_CLIENT_SECRET = "my-outbound-client-secret";
	private static final String OUTBOUND_SCOPE = "offline openid";
	public static final String SCOPE_DELIMITER = " ";
	private static final String OUTBOUND_TOKEN_URI = "https://tokenuri";
	@Mock
	private OAuth2RestTemplate oAuth2RestTemplate;
	private String hostUrl;
	private SyncedUser syncedUser;

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());

	@Before
	public void setUp() throws Exception {
		oAuth2RestTemplate = mock(OAuth2RestTemplate.class);
		syncedUser = createSyncedUser();
		hostUrl = new URIBuilder().setScheme("http").setHost(HOST).setPort(wireMockRule.port()).build().toString();
	}

	@Test
	public void postUserAssignment_returnsAccepted() throws Exception {
		RestTemplateFactory restTemplateFactory = new UserSyncRestTemplateFactoryImpl();
		UserSyncApiClient userSyncApiClient = new UserSyncApiClient(restTemplateFactory);

		//Given
		stubFor(post(urlEqualTo(USER_SYNC_ENDPOINT))
				.willReturn(aResponse()
						.withStatus(200)));

		//When
		userSyncApiClient.syncUserAssignment(hostUrl, OAUTH_KEY, OAUTH_SECRET, syncedUser);

		//Then
		verify(exactly(1), postRequestedFor(urlMatching(USER_SYNC_ENDPOINT))
				.withRequestBody(equalToJson(resourceAsString("usersync/usersync-assign.json")))
				.withHeader("Content-Type", matching("application/json;charset=UTF-8")));
	}

	@Test
	public void postUserAssignment_oAuth2_returnsAccepted() throws Exception {
		RestTemplateFactory restTemplateFactory = mock(RestTemplateFactory.class);
		UserSyncApiClient userSyncApiClient = new UserSyncApiClient(restTemplateFactory);
		
		when(restTemplateFactory.getOAuth2RestTemplate(any(OAuth2ProtectedResourceDetails.class))).thenReturn(oAuth2RestTemplate);

		userSyncApiClient.syncUserAssignment(hostUrl, createOAuth2ProtectedResourceDetails(), syncedUser);

		Mockito.verify(oAuth2RestTemplate).postForEntity(anyString(), any(), any());
	}

	private OAuth2ProtectedResourceDetails createOAuth2ProtectedResourceDetails() {
		ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
		details.setId(OUTBOUND_CLIENT_ID);
		details.setClientId(OUTBOUND_CLIENT_ID);
		details.setClientSecret(OUTBOUND_CLIENT_SECRET);
		details.setAccessTokenUri(OUTBOUND_TOKEN_URI);
		details.setScope(Arrays.asList(OUTBOUND_SCOPE.split(SCOPE_DELIMITER)));
		return details;
	}

	@Test
	public void postUserAssignment_throwsUserApiException() throws Exception {
		RestTemplateFactory restTemplateFactory = new UserSyncRestTemplateFactoryImpl();
		UserSyncApiClient userSyncApiClient = new UserSyncApiClient(restTemplateFactory);
		//Given
		String responseBody = "{\"code\":\"SUBSCRIPTION_NOT_FOUND\", \"message\":\"Subscription is not found for the ISV\"}";
		stubFor(post(urlEqualTo(USER_SYNC_ENDPOINT))
				.willReturn(aResponse()
						.withStatus(404)
						.withHeader("Content-Type", "application/json")
						.withBody(responseBody)));
		//Then
		assertThatThrownBy(() -> userSyncApiClient.syncUserAssignment(hostUrl, OAUTH_KEY, OAUTH_SECRET, syncedUser))
				.isInstanceOf(UserSyncException.class)
				.hasMessage("Subscription is not found for the ISV")
				.hasFieldOrPropertyWithValue("code", "SUBSCRIPTION_NOT_FOUND");
	}

	@Test
	public void postUserAssignment_throwsUserSyncTooManyRequestsException() throws Exception {
		RestTemplateFactory restTemplateFactory = new UserSyncRestTemplateFactoryImpl();
		UserSyncApiClient userSyncApiClient = new UserSyncApiClient(restTemplateFactory);
		
		//Given
		stubFor(post(urlEqualTo(USER_SYNC_ENDPOINT))
				.willReturn(aResponse()
						.withStatus(429)));

		//Then
		assertThatThrownBy(() -> userSyncApiClient.syncUserUnAssignment(hostUrl, OAUTH_KEY, OAUTH_SECRET, syncedUser))
				.isInstanceOf(UserSyncTooManyRequestsException.class);
	}

	@Test
	public void postUserUnAssignment_returnsAccepted() throws Exception {
		RestTemplateFactory restTemplateFactory = new UserSyncRestTemplateFactoryImpl();
		UserSyncApiClient userSyncApiClient = new UserSyncApiClient(restTemplateFactory);
		//Given
		stubFor(post(urlEqualTo(USER_SYNC_ENDPOINT))
				.willReturn(aResponse()
						.withStatus(200)));

		//When
		userSyncApiClient.syncUserUnAssignment(hostUrl, OAUTH_KEY, OAUTH_SECRET, syncedUser);

		//Then
		verify(exactly(1), postRequestedFor(urlMatching(USER_SYNC_ENDPOINT))
				.withRequestBody(equalToJson(resourceAsString("usersync/usersync-unassign.json")))
				.withHeader("Content-Type", matching("application/json;charset=UTF-8")));
	}

	@Test
	public void postUserUnAssignment_throwsUserApiException() throws Exception {
		RestTemplateFactory restTemplateFactory = new UserSyncRestTemplateFactoryImpl();
		UserSyncApiClient userSyncApiClient = new UserSyncApiClient(restTemplateFactory);
		//Given
		String responseBody = "{\"code\":\"SUBSCRIPTION_NOT_FOUND\", \"message\":\"Subscription is not found for the ISV\"}";
		stubFor(post(urlEqualTo(USER_SYNC_ENDPOINT))
				.willReturn(aResponse()
						.withStatus(404)
						.withHeader("Content-Type", "application/json")
						.withBody(responseBody)));
		//Then
		assertThatThrownBy(() -> userSyncApiClient.syncUserUnAssignment(hostUrl, OAUTH_KEY, OAUTH_SECRET, syncedUser))
				.isInstanceOf(UserSyncException.class)
				.hasMessage("Subscription is not found for the ISV")
				.hasFieldOrPropertyWithValue("code", "SUBSCRIPTION_NOT_FOUND");
	}

	@Test
	public void postUserUnAssignment_throwsUserSyncTooManyRequestsException() throws Exception {
		RestTemplateFactory restTemplateFactory = new UserSyncRestTemplateFactoryImpl();
		UserSyncApiClient userSyncApiClient = new UserSyncApiClient(restTemplateFactory);
		//Given
		stubFor(post(urlEqualTo(USER_SYNC_ENDPOINT))
				.willReturn(aResponse()
						.withStatus(429)));
		//Then
		assertThatThrownBy(() -> userSyncApiClient.syncUserUnAssignment(hostUrl, OAUTH_KEY, OAUTH_SECRET, syncedUser))
				.isInstanceOf(UserSyncTooManyRequestsException.class);
	}

	@Test
	public void postUserUnAssignmentInvalidJson_throwsUseSyncException() throws Exception {
		RestTemplateFactory restTemplateFactory = new UserSyncRestTemplateFactoryImpl();
		UserSyncApiClient userSyncApiClient = new UserSyncApiClient(restTemplateFactory);
		//Given
		String responseBody = "{\"code\":\"SUBSCRIPTION_NOT_FOUND\", \"error\":\"Subscription is not found for the ISV\"}";
		stubFor(post(urlEqualTo(USER_SYNC_ENDPOINT))
				.willReturn(aResponse()
						.withStatus(404)
						.withHeader("Content-Type", "application/json")
						.withBody(responseBody)));
		//Then
		assertThatThrownBy(() -> userSyncApiClient.syncUserUnAssignment(hostUrl, OAUTH_KEY, OAUTH_SECRET, syncedUser))
				.isInstanceOf(UserSyncException.class)
				.hasFieldOrPropertyWithValue("code", "UNKNOWN_ERROR");
	}

	private SyncedUser createSyncedUser() {
		SyncedUser syncedUser = new SyncedUser();
		syncedUser.setDeveloperIdentifier("6b4bd452-895d-4098-aa56-e6046b238e0f");
		syncedUser.setAccountIdentifier("160744112");
		syncedUser.setEmail("tester1@goog-test.junittest.appdirect.co");
		syncedUser.setUserIdentifier("513");
		syncedUser.setFirstName("John");
		syncedUser.setLastName("Doe");
		syncedUser.setUserName("tester1");
		return syncedUser;
	}
}
