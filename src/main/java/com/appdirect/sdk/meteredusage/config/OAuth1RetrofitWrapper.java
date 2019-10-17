package com.appdirect.sdk.meteredusage.config;

import org.springframework.stereotype.Component;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;

@Component
public class OAuth1RetrofitWrapper {
	private Retrofit.Builder retrofitBuilder;

	public OAuth1RetrofitWrapper() {
		this.retrofitBuilder = new Retrofit.Builder().addConverterFactory(JacksonConverterFactory.create());
	}

	/**
	 * Method used to set the baseUrl for the requests
	 */
	public OAuth1RetrofitWrapper baseUrl(String baseUrl) {
		retrofitBuilder = retrofitBuilder.baseUrl(baseUrl);
		return this;
	}

	/**
	 * Method used to sign with the given oauthConsumerKey and oauthConsumerSecret
	 */
	public OAuth1RetrofitWrapper sign(String oauthConsumerKey, String oauthConsumerSecret) {
		configureOkHttpClient(oauthConsumerKey, oauthConsumerSecret);
		return this;
	}

	/**
	 * Method used to obtain the {@link Retrofit} class used for the API calls
	 */
	public Retrofit build() {
		return retrofitBuilder.build();
	}

	private void configureOkHttpClient(String oauthConsumerKey, String oauthConsumerSecret) {
		final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

		// Add a logging interceptor to capture incoming/outgoing calls.
		HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
		httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);

		OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(oauthConsumerKey, oauthConsumerSecret);

		okHttpClientBuilder.addNetworkInterceptor(new ChainableSigningInterceptor(consumer));
		retrofitBuilder = retrofitBuilder.client(okHttpClientBuilder.build());
	}
}
