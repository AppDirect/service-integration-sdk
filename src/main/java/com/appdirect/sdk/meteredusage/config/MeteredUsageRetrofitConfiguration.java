package com.appdirect.sdk.meteredusage.config;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@RequiredArgsConstructor
public class MeteredUsageRetrofitConfiguration {

	@Value("${appdirect.baseUrl:https://metered-usage}")
	private String baseUrl;

	@Bean(name = "meteredUsageRetrofitBuilder")
	@Qualifier("meteredUsageRetrofitBuilder")
	public Retrofit.Builder meteredUsageRetrofitBuilder() {
		return new Retrofit.Builder()
				.baseUrl(baseUrl)
				.addConverterFactory(jacksonConverterFactory());
	}

	private JacksonConverterFactory jacksonConverterFactory() {
		return JacksonConverterFactory.create();
	}
}
