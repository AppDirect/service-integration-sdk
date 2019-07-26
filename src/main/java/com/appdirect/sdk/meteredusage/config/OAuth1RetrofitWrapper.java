package com.appdirect.sdk.meteredusage.config;

import org.springframework.stereotype.Component;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;

@Component
public class OAuth1RetrofitWrapper {
	private Retrofit.Builder builder;

	public OAuth1RetrofitWrapper(String baseUrl) {
		this.builder = new Retrofit.Builder()
				.baseUrl(baseUrl)
				.addConverterFactory(JacksonConverterFactory.create());
	}

	public Retrofit sign(String key, String secret) {
		return builder.client(getOkHttpOAuthConsumer(key, secret))
				.build();
	}

	private OkHttpClient getOkHttpOAuthConsumer(String key, String secret) {
		final OkHttpClient.Builder builder = new OkHttpClient.Builder();

		// Add a logging interceptor to capture incoming/outgoing calls.
		HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
		httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		builder.addInterceptor(httpLoggingInterceptor);

		OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(key, secret);

		builder.addNetworkInterceptor(new ChainableSigningInterceptor(consumer));
		return builder.build();
	}
}
