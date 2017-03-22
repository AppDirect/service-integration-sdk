package com.appdirect.sdk.appmarket;

import lombok.Value;

/**
 * Data object representing the (developerKey, developerSecret) pair shared by any connector using the SDK and the
 * AppMarket. This information is used for signing communications between the two systems
 */
@Value
public class Credentials {
	public final String developerKey;
	public final String developerSecret;

	public static Credentials invalidCredentials() {
		return new Credentials("this key does not exist in the supplier", "this key does not exist in the supplier");
	}
}
