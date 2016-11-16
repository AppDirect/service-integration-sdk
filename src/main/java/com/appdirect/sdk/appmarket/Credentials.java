package com.appdirect.sdk.appmarket;

public class Credentials {
	public final String developerKey;
	public final String developerSecret;

	public Credentials(String developerKey, String developerSecret) {
		this.developerKey = developerKey;
		this.developerSecret = developerSecret;
	}

	public static Credentials invalidCredentials() {
		return new Credentials("this key does not exist in the supplier", "this key does not exist in the supplier");
	}
}
