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
		UserSyncApiResult userSyncApiResult = UserSyncApiResult.builder().build();
		ArgumentCaptor<UserSyncRequestPayload> captor = ArgumentCaptor.forClass(UserSyncRequestPayload.class);

		when(restOperationsFactory.restOperationsForProfile(OAUTH_KEY, OAUTH_SECRET))
				.thenReturn(restOperations);
		when(restOperations.postForEntity(anyString(), any(UserSyncRequestPayload.class), any())).thenReturn(ResponseEntity.accepted().body(userSyncApiResult));

		//When
		userSyncApiClient.syncUserAssignment(BASE_URL, OAUTH_KEY, OAUTH_SECRET, syncedUser);

		//Then
		verify(restOperations, Mockito.times(1)).postForEntity(anyString(), captor.capture(), any());
		UserSyncRequestPayload result = captor.getValue();
		assertThat(result.getAccountIdentifier()).isEqualTo(syncedUser.getAccountIdentifier());
		assertThat(result.getDeveloperIdentifier()).isEqualTo(syncedUser.getDeveloperIdentifier());
		assertThat(result.getEmail()).isEqualTo(syncedUser.getEmail());
		assertThat(result.getType()).isEqualTo("ASSIGNMENT");
		assertThat(result.getOperation()).isEqualTo(UserSyncRequestPayloadOperation.ASSIGN.toString());
	}

	@Test
	public void postUserUnAssignment_returnsAccepted() throws Exception {
		//Given
		UserSyncApiResult userSyncApiResult = UserSyncApiResult.builder().build();
		ArgumentCaptor<UserSyncRequestPayload> captor = ArgumentCaptor.forClass(UserSyncRequestPayload.class);

		when(restOperationsFactory.restOperationsForProfile(OAUTH_KEY, OAUTH_SECRET))
				.thenReturn(restOperations);
		when(restOperations.postForEntity(anyString(), any(UserSyncRequestPayload.class), any())).thenReturn(ResponseEntity.accepted().body(userSyncApiResult));

		//When
		userSyncApiClient.syncUserUnAssignment(BASE_URL, OAUTH_KEY, OAUTH_SECRET, syncedUser);

		//Then
		verify(restOperations, Mockito.times(1)).postForEntity(anyString(), captor.capture(), any());
		UserSyncRequestPayload result = captor.getValue();
		assertThat(result.getAccountIdentifier()).isEqualTo(syncedUser.getAccountIdentifier());
		assertThat(result.getDeveloperIdentifier()).isEqualTo(syncedUser.getDeveloperIdentifier());
		assertThat(result.getType()).isEqualTo("ASSIGNMENT");
		assertThat(result.getOperation()).isEqualTo(UserSyncRequestPayloadOperation.UNASSIGN.toString());
	}


	private SyncedUser createSyncedUser() {
		SyncedUser syncedUser = new SyncedUser();
		syncedUser.setDeveloperIdentifier("developerId");
		syncedUser.setAccountIdentifier("accountId");
		syncedUser.setEmail("email@email.org");
		syncedUser.setUserIdentifier("userId");
		return syncedUser;
	}
}
