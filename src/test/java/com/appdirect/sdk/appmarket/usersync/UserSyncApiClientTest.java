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

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.appdirect.sdk.web.RestOperationsFactory;

public class UserSyncApiClientTest {
	private RestOperationsFactory restOperationsFactory;
	private RestTemplate restOperations;
	private UserSyncApiClient userSyncApiClient;
	private static final String BASE_URL = "https://orchardbox.appdirect.com";
	private static final String OAUTH_KEY = "testKey";
	private static final String OAUTH_SECRET = "testSecret";
	private SyncedUser syncedUser;

	@Before
	public void setUp() throws Exception {
		restOperationsFactory = mock(RestOperationsFactory.class);
		restOperations = mock(RestTemplate.class);
		userSyncApiClient = new UserSyncApiClient(restOperationsFactory);
		syncedUser = createSyncedUser();
	}

	@Test
	public void postUserAssignment_returnsAccepted() throws Exception {
		//Given
		ArgumentCaptor<UserSyncRequestPayload> captor = ArgumentCaptor.forClass(UserSyncRequestPayload.class);

		when(restOperationsFactory.restOperationsForProfile(OAUTH_KEY, OAUTH_SECRET))
				.thenReturn(restOperations);
		when(restOperations.postForEntity(anyString(), any(UserSyncRequestPayload.class), any())).thenReturn(ResponseEntity.accepted().body(null));

		//When
		userSyncApiClient.syncUserAssignment(BASE_URL, OAUTH_KEY, OAUTH_SECRET, syncedUser);

		//Then
		verify(restOperations, Mockito.times(1)).postForEntity(anyString(), captor.capture(), any());
		UserSyncRequestPayload expected = createExpectedPayload(syncedUser, UserSyncRequestPayloadOperation.ASSIGN);
		UserSyncRequestPayload result = captor.getValue();
		assertThat(result).isEqualTo(expected);
	}

	@Test
	public void postUserUnAssignment_returnsAccepted() throws Exception {
		//Given
		ArgumentCaptor<UserSyncRequestPayload> captor = ArgumentCaptor.forClass(UserSyncRequestPayload.class);

		when(restOperationsFactory.restOperationsForProfile(OAUTH_KEY, OAUTH_SECRET))
				.thenReturn(restOperations);
		when(restOperations.postForEntity(anyString(), any(UserSyncRequestPayload.class), any())).thenReturn(ResponseEntity.accepted().body(null));

		//When
		userSyncApiClient.syncUserUnAssignment(BASE_URL, OAUTH_KEY, OAUTH_SECRET, syncedUser);

		//Then
		verify(restOperations, Mockito.times(1)).postForEntity(anyString(), captor.capture(), any());
		UserSyncRequestPayload result = captor.getValue();
		UserSyncRequestPayload expected = createExpectedPayload(syncedUser, UserSyncRequestPayloadOperation.UNASSIGN);
		assertThat(result).isEqualTo(expected);
	}

	private SyncedUser createSyncedUser() {
		SyncedUser syncedUser = new SyncedUser();
		syncedUser.setDeveloperIdentifier("developerId");
		syncedUser.setAccountIdentifier("accountId");
		syncedUser.setEmail("email@email.org");
		syncedUser.setUserIdentifier("userId");
		return syncedUser;
	}

	private UserSyncRequestPayload createExpectedPayload(SyncedUser syncedUser, UserSyncRequestPayloadOperation operation) {
		return UserSyncRequestPayload.builder()
				.accountIdentifier(syncedUser.getAccountIdentifier())
				.developerIdentifier(syncedUser.getDeveloperIdentifier())
				.email(syncedUser.getEmail())
				.type("ASSIGNMENT")
				.userIdentifier(syncedUser.getUserIdentifier())
				.operation(operation.toString())
				.build();
	}
}
