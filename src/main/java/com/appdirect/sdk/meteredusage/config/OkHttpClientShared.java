package com.appdirect.sdk.meteredusage.config;

import okhttp3.OkHttpClient;

public class OkHttpClientShared extends OkHttpClient {

	private static class OkHttpClientInitializer {
		private static final OkHttpClientShared instance = new OkHttpClientShared();
	}

	public static OkHttpClientShared getInstance() {
		return OkHttpClientInitializer.instance;
	}
}
