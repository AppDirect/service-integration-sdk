package com.appdirect.sdk.appmarket.usersync;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.appdirect.sdk.exception.UserSyncException;
import com.appdirect.sdk.web.RestOperationsFactory;

/**
 * This class defines method for performing HTTP requests for User Sync against an AppMarket instance
 */
@Slf4j
@RequiredArgsConstructor
public class UserSyncApiClient {
	private final RestOperationsFactory restClientFactory;
	private final String USER_SYNC_ASSIGN_API_ENDPOINT = "%s/api/sync/v1/tasks";
	private final String USER_SYNC_PAYLOAD_TYPE = "ASSIGNMENT";

	public UserSyncApiResult syncUserAssignment(String baseUrl, String oauthKey, String oauthSecret, UserSyncInfo userSyncInfo) {
		String url = String.format(USER_SYNC_ASSIGN_API_ENDPOINT, baseUrl);
		log.debug("Calling user sync assign api with url={}", url);

		UserSyncRequestPayload userSyncRequestPayload = buildUserSyncRequestPayload(userSyncInfo);
		userSyncRequestPayload.setType(USER_SYNC_PAYLOAD_TYPE);
		userSyncRequestPayload.setOperation(UserSyncRequestPayloadOperation.ASSIGN.toString());

		try {
			ResponseEntity<UserSyncApiResult> apiResult = restClientFactory
					.restOperationsForProfile(oauthKey, oauthSecret)
					.postForEntity(url, userSyncRequestPayload, UserSyncApiResult.class);
			return populateUserSyncResult(apiResult);
		} catch (UserSyncException e) {
			return e.getResult();
		}
	}

	public UserSyncApiResult syncUserUnAssignment(String baseUrl, String oauthKey, String oauthSecret, UserSyncInfo userSyncInfo) {
		String url = String.format(USER_SYNC_ASSIGN_API_ENDPOINT, baseUrl);
		log.debug("Calling user sync unassign api with url={}", url);
		UserSyncRequestPayload userSyncRequestPayload = buildUserSyncRequestPayload(userSyncInfo);
		userSyncRequestPayload.setType(USER_SYNC_PAYLOAD_TYPE);
		userSyncRequestPayload.setOperation(UserSyncRequestPayloadOperation.UNASSIGN.toString());

		try {
			ResponseEntity<UserSyncApiResult> apiResult = restClientFactory
					.restOperationsForProfile(oauthKey, oauthSecret)
					.postForEntity(url, userSyncRequestPayload, UserSyncApiResult.class);
			return populateUserSyncResult(apiResult);
		} catch (UserSyncException e) {
			return e.getResult();
		}
	}


	private UserSyncApiResult populateUserSyncResult(ResponseEntity<UserSyncApiResult> apiResult) {
		if (apiResult.getStatusCode() == HttpStatus.ACCEPTED) {
			return UserSyncApiResult.builder().status(apiResult.getStatusCode()).build();
		} else {
			UserSyncApiResult result = apiResult.getBody();
			result.setStatus(apiResult.getStatusCode());
			return result;
		}
	}

	private UserSyncRequestPayload buildUserSyncRequestPayload(UserSyncInfo userSyncInfo) {
		return UserSyncRequestPayload.builder().
				userIdentifier(userSyncInfo.getUserIdentifier()).userName(userSyncInfo.getUserName())
				.accountIdentifier(userSyncInfo.getAccountIdentifier()).developerIdentifier(userSyncInfo.getDeveloperIdentifier())
				.email(userSyncInfo.getEmail())
				.firstName(userSyncInfo.getFirstName()).lastName(userSyncInfo.getLastName()).build();
	}
}
