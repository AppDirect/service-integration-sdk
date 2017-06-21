package com.appdirect.sdk.appmarket.usersync;

import lombok.Data;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSyncInfo {
	@NotBlank
	private String accountIdentifier;
	@NotBlank
	private String developerIdentifier;
	@NotBlank
	private String userIdentifier;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
}
