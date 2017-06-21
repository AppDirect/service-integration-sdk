package com.appdirect.sdk.appmarket.usersync;

import lombok.Builder;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/*
Internal class to the SDK. Populated to form the request body of User Sync requests
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserSyncRequestPayload {
	private String type;
	private String operation;
	private String accountIdentifier;
	private String developerIdentifier;
	private String userIdentifier;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
}
