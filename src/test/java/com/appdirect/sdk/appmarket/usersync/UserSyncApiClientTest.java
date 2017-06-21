package com.appdirect.sdk.appmarket.usersync;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
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


	@Before
	public void setUp() throws Exception {
		restOperationsFactory = mock(RestOperationsFactory.class);
		restOperations = mock(RestTemplate.class);
		userSyncApiClient = new UserSyncApiClient(restOperationsFactory);
	}

	@Test
	public void postUserAssignment_returnsAccepted() throws Exception {
		//Given
		UserSyncApiResult userSyncApiResult = UserSyncApiResult.builder().status(HttpStatus.ACCEPTED).build();
		UserSyncInfo userSyncInfo = new UserSyncInfo();
		when(restOperationsFactory.restOperationsForProfile(OAUTH_KEY, OAUTH_SECRET))
				.thenReturn(restOperations);
		when(restOperations.postForEntity(anyString(), any(UserSyncRequestPayload.class), any())).thenReturn(ResponseEntity.accepted().body(userSyncApiResult));

		//When
		UserSyncApiResult apiResult = userSyncApiClient.syncUserAssignment(BASE_URL, OAUTH_KEY, OAUTH_SECRET, userSyncInfo);

		//Then
		assertThat(apiResult).isEqualTo(userSyncApiResult);
	}

	@Test
	public void postUserAssignment_returnsNotFound() throws Exception {
		//Given
		UserSyncApiResult userSyncApiResult = createUserSyncApiResult(HttpStatus.NOT_FOUND, "ISV_Subscription not found");
		UserSyncInfo userSyncInfo = new UserSyncInfo();
		when(restOperationsFactory.restOperationsForProfile(OAUTH_KEY, OAUTH_SECRET))
				.thenReturn(restOperations);
		when(restOperations.postForEntity(anyString(), any(UserSyncRequestPayload.class), any())).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(userSyncApiResult));

		//When
		UserSyncApiResult apiResult = userSyncApiClient.syncUserAssignment(BASE_URL, OAUTH_KEY, OAUTH_SECRET, userSyncInfo);

		//Then
		assertThat(apiResult).isEqualTo(userSyncApiResult);
	}

	@Test
	public void postUserAssignment_returnsTooManyRequestsError() throws Exception {
		//Given
		UserSyncApiResult userSyncApiResult = createUserSyncApiResult(HttpStatus.TOO_MANY_REQUESTS, "");
		UserSyncInfo userSyncInfo = new UserSyncInfo();
		when(restOperationsFactory.restOperationsForProfile(OAUTH_KEY, OAUTH_SECRET))
				.thenReturn(restOperations);
		when(restOperations.postForEntity(anyString(), any(UserSyncRequestPayload.class), any())).thenReturn(ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(userSyncApiResult));

		//When
		UserSyncApiResult apiResult = userSyncApiClient.syncUserAssignment(BASE_URL, OAUTH_KEY, OAUTH_SECRET, userSyncInfo);

		//Then
		assertThat(apiResult).isEqualTo(userSyncApiResult);
	}

	@Test
	public void postUserUnAssignment_returnsAccepted() throws Exception {
		//Given
		UserSyncApiResult userSyncApiResult = UserSyncApiResult.builder().status(HttpStatus.ACCEPTED).build();
		UserSyncInfo userSyncInfo = new UserSyncInfo();
		when(restOperationsFactory.restOperationsForProfile(OAUTH_KEY, OAUTH_SECRET))
				.thenReturn(restOperations);
		when(restOperations.postForEntity(anyString(), any(UserSyncRequestPayload.class), any())).thenReturn(ResponseEntity.accepted().body(userSyncApiResult));

		//When
		UserSyncApiResult apiResult = userSyncApiClient.syncUserUnAssignment(BASE_URL, OAUTH_KEY, OAUTH_SECRET, userSyncInfo);

		//Then
		assertThat(apiResult).isEqualTo(userSyncApiResult);
	}

	@Test
	public void postUserUnAssignment_returnsNotFound() throws Exception {
		//Given
		UserSyncApiResult userSyncApiResult = createUserSyncApiResult(HttpStatus.NOT_FOUND, "ISV_Subscription not found");
		UserSyncInfo userSyncInfo = new UserSyncInfo();
		when(restOperationsFactory.restOperationsForProfile(OAUTH_KEY, OAUTH_SECRET))
				.thenReturn(restOperations);
		when(restOperations.postForEntity(anyString(), any(UserSyncRequestPayload.class), any())).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(userSyncApiResult));

		//When
		UserSyncApiResult apiResult = userSyncApiClient.syncUserUnAssignment(BASE_URL, OAUTH_KEY, OAUTH_SECRET, userSyncInfo);

		//Then
		assertThat(apiResult).isEqualTo(userSyncApiResult);
	}

	@Test
	public void postUserUnAssignment_returnsTooManyRequestsError() throws Exception {
		//Given
		UserSyncApiResult userSyncApiResult = createUserSyncApiResult(HttpStatus.TOO_MANY_REQUESTS, "");
		UserSyncInfo userSyncInfo = new UserSyncInfo();
		when(restOperationsFactory.restOperationsForProfile(OAUTH_KEY, OAUTH_SECRET))
				.thenReturn(restOperations);
		when(restOperations.postForEntity(anyString(), any(UserSyncRequestPayload.class), any())).thenReturn(ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(userSyncApiResult));

		//When
		UserSyncApiResult apiResult = userSyncApiClient.syncUserUnAssignment(BASE_URL, OAUTH_KEY, OAUTH_SECRET, userSyncInfo);

		//Then
		assertThat(apiResult).isEqualTo(userSyncApiResult);
	}

	private UserSyncApiResult createUserSyncApiResult(HttpStatus status, String error) {
		return UserSyncApiResult.builder().status(status).code(error).message(error).build();
	}
}
