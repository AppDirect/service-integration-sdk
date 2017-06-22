package com.appdirect.sdk.appmarket.usersync;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SyncedUser {
	private String accountIdentifier;
	private String developerIdentifier;
	private String userIdentifier;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;

	@JsonCreator
	SyncedUser(@JsonProperty(value = "accountIdentifier", required = true) String accountIdentifier, @JsonProperty(value = "developerIdentifier", required = true) String developerIdentifier, @JsonProperty(value = "userIdentifier") String userIdentifier,
						 @JsonProperty(value = "userName") String userName, @JsonProperty(value = "firstName") String firstName, @JsonProperty(value = "lastName") String lastName, @JsonProperty(value = "email") String email) {
		this.accountIdentifier = accountIdentifier;
		this.developerIdentifier = developerIdentifier;
		this.userIdentifier = userIdentifier;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
}
