package com.appdirect.sdk.appmarket;

import lombok.Value;

@Value
public class Credentials {
	public final String developerKey;
	public final String developerSecret;

	public static Credentials invalidCredentials() {
		return new Credentials("this key does not exist in the supplier", "this key does not exist in the supplier");
	}
}
