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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;

import com.appdirect.sdk.exception.UserSyncException;
import com.appdirect.sdk.exception.UserSyncTooManyRequestsException;
import com.appdirect.sdk.web.oauth.RestTemplateFactory;

/**
 * This class defines method for performing HTTP requests for User Sync against an AppMarket instance
 * {@link UserSyncException} Appmarket has failed to sync the user
 * {@link UserSyncTooManyRequestsException} Appmarket not processing requests due to high volume of requests
 */
@Slf4j
@RequiredArgsConstructor
public class UserSyncApiClient {
	private final RestTemplateFactory restTemplateFactory;
	private final String USER_SYNC_ASSIGN_API_ENDPOINT = "%s/api/sync/v1/tasks";
	private final String USER_SYNC_PAYLOAD_TYPE = "ASSIGNMENT";

	public void syncUserAssignment(String baseUrl, String oauthKey, String oauthSecret, SyncedUser syncedUser) {
		String url = String.format(USER_SYNC_ASSIGN_API_ENDPOINT, baseUrl);
		log.debug("Calling user sync assign api with url={}", url);

		UserSyncRequestPayload userSyncRequestPayload = buildUserSyncRequestPayload(syncedUser);
		userSyncRequestPayload.setType(USER_SYNC_PAYLOAD_TYPE);
		userSyncRequestPayload.setOperation(UserSyncRequestPayloadOperation.ASSIGN.toString());

		ResponseEntity<String> apiResult = restTemplateFactory
				.getOAuthRestTemplate(oauthKey, oauthSecret)
				.postForEntity(url, userSyncRequestPayload, String.class);
		log.info("Received response={} for sync assign api", apiResult);
	}

	public void syncUserUnAssignment(String baseUrl, String oauthKey, String oauthSecret, SyncedUser syncedUser) {
		String url = String.format(USER_SYNC_ASSIGN_API_ENDPOINT, baseUrl);
		log.debug("Calling user sync unassign api with url={}", url);
		UserSyncRequestPayload userSyncRequestPayload = buildUserSyncRequestPayload(syncedUser);
		userSyncRequestPayload.setType(USER_SYNC_PAYLOAD_TYPE);
		userSyncRequestPayload.setOperation(UserSyncRequestPayloadOperation.UNASSIGN.toString());

		ResponseEntity<String> apiResult = restTemplateFactory
				.getOAuthRestTemplate(oauthKey, oauthSecret)
				.postForEntity(url, userSyncRequestPayload, String.class);
		log.info("Received response={} for sync un-assign api", apiResult);
	}

	private UserSyncRequestPayload buildUserSyncRequestPayload(SyncedUser syncedUser) {
		return UserSyncRequestPayload.builder()
				.userIdentifier(syncedUser.getUserIdentifier())
				.userName(syncedUser.getUserName())
				.accountIdentifier(syncedUser.getAccountIdentifier())
				.developerIdentifier(syncedUser.getDeveloperIdentifier())
				.email(syncedUser.getEmail())
				.firstName(syncedUser.getFirstName())
				.lastName(syncedUser.getLastName())
				.build();
	}
}
