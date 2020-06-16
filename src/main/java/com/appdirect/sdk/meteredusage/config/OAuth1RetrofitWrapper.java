package com.appdirect.sdk.meteredusage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import brave.Tracing;
import brave.okhttp3.TracingInterceptor;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;

@Component
public class OAuth1RetrofitWrapper {

	@Autowired(required = false)
	private Tracing tracing;

	private Retrofit.Builder retrofitBuilder;
	private OkHttpClient okHttpClient;

	@Autowired
	public OAuth1RetrofitWrapper(OkHttpClient okHttpClient) {
		this.retrofitBuilder = new Retrofit.Builder().addConverterFactory(JacksonConverterFactory.create());
		this.okHttpClient = okHttpClient;
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
		OkHttpClient.Builder okHttpClientBuilder = okHttpClient.newBuilder();
		if(tracing != null){
			okHttpClientBuilder.dispatcher(new Dispatcher(tracing.currentTraceContext().executorService(new Dispatcher().executorService())))
				.addNetworkInterceptor(TracingInterceptor.create(tracing));
		}

		// Add a logging interceptor to capture incoming/outgoing calls.
		HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
		httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);

		OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(oauthConsumerKey, oauthConsumerSecret);

		okHttpClientBuilder.addNetworkInterceptor(new ChainableSigningInterceptor(consumer));
		retrofitBuilder = retrofitBuilder.client(okHttpClientBuilder.build());
	}
}
