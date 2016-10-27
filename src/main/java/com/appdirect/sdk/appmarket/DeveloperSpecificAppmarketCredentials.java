package com.appdirect.sdk.appmarket;

import lombok.EqualsAndHashCode;

/**
 * Represents the credentials needed to authenticate with the AppMarket
 */
@EqualsAndHashCode
public class DeveloperSpecificAppmarketCredentials {
	private final String developerKey;
	private final String developerSecret;

	public DeveloperSpecificAppmarketCredentials(String developerKey, String developerSecret) {
		this.developerKey = developerKey;
		this.developerSecret = developerSecret;
	}

	public String getDeveloperKey() {
		return developerKey;
	}

	public String getDeveloperSecret() {
		return developerSecret;
	}
}
