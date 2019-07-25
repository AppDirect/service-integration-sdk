package com.appdirect.sdk.meteredUsage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;

@Configuration
public class MeteredUsageRetrofitConfiguration {

	@Autowired
	public MeteredUsageRetrofitConfiguration() {

	}

	@Bean(name = "meteredUsageRetrofitBuilder")
	@Qualifier("meteredUsageRetrofitBuilder")
	public Retrofit.Builder meteredUsageRetrofitBuilder() {
		Retrofit.Builder builder = new Retrofit.Builder()
				.addConverterFactory(jacksonConverterFactory());

		return builder;
	}

	public static OkHttpClient getOkHttpOAuthConsumer(String key, String secret) {
		final OkHttpClient.Builder builder = new OkHttpClient.Builder();

		// Add a logging interceptor to capture incoming/outgoing calls.
		HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
		httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		builder.addInterceptor(httpLoggingInterceptor);

		OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(key, secret);

		builder.addNetworkInterceptor(new ChainableSigningInterceptor(consumer));
		return builder.build();
	}

	private JacksonConverterFactory jacksonConverterFactory() {
		return JacksonConverterFactory.create();
	}
}
