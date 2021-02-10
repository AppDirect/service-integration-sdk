/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appdirect.sdk.appmarket.events;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import com.appdirect.sdk.appmarket.BasicAuthCredentialsSupplier;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.migration.AppmarketMigrationController;
import com.appdirect.sdk.appmarket.migration.AppmarketMigrationService;
import com.appdirect.sdk.appmarket.migration.CustomerAccountValidationHandler;
import com.appdirect.sdk.appmarket.migration.SubscriptionMigrationHandler;
import com.appdirect.sdk.appmarket.migration.SubscriptionValidationHandler;
import com.appdirect.sdk.meteredusage.config.OAuth1RetrofitWrapper;
import com.appdirect.sdk.meteredusage.service.MeteredUsageApiClientService;
import com.appdirect.sdk.meteredusage.service.MeteredUsageApiClientServiceImpl;
import com.appdirect.sdk.web.exception.AppmarketEventClientExceptionHandler;
import com.appdirect.sdk.web.oauth.BasicAuthUserExtractor;
import com.appdirect.sdk.web.oauth.DefaultRestTemplateFactoryImpl;
import com.appdirect.sdk.web.oauth.OAuth2ClientDetailsService;
import com.appdirect.sdk.web.oauth.OAuthKeyExtractor;
import com.appdirect.sdk.web.oauth.ReportUsageRestTemplateFactoryImpl;
import com.appdirect.sdk.web.oauth.RestTemplateFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

@EnableRetry
@Configuration
public class AppmarketCommunicationConfiguration {

	@Bean
	public AppmarketBillingClient appmarketBillingReporter(DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier,
														   @Qualifier("sdkInternalJsonMapper") ObjectMapper mapper) {
		return new AppmarketBillingClient(billingRestTemplateFactory(), credentialsSupplier, mapper);
	}

	@Bean
	public MeteredUsageApiClientService meteredUsageApiClientService(DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier, OAuth1RetrofitWrapper oAuth1RetrofitWrapper) {
		return new MeteredUsageApiClientServiceImpl(credentialsSupplier, oAuth1RetrofitWrapper);
	}

	@Bean
	public OAuth1RetrofitWrapper oAuth1RetrofitWrapper(OkHttpClient okHttpClient) {
		return new OAuth1RetrofitWrapper(okHttpClient);
	}

	@Bean
	public OkHttpClient okHttpClient(@Value("${usage.api.timeout.connectTimeout:10000}") int connectTimeout, @Value("${usage.api.timeout.readTimeout:10000}") int readTimeout, @Value("${usage.api.timeout.writeTimeout:10000}") int writeTimeout) {
		return new OkHttpClient().newBuilder()
				.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
				.readTimeout(readTimeout, TimeUnit.MILLISECONDS)
				.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
				.build();
	}

	@Bean
	public AppmarketEventClient appmarketEventFetcher(DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier,
													  @Qualifier("sdkInternalJsonMapper") ObjectMapper mapper) {
		return new AppmarketEventClient(appMarketRestTemplateFactory(), credentialsSupplier, mapper);
	}

	@Bean
	public AppmarketEventService appmarketEventService(DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier,
																										 AppmarketEventDispatcher eventDispatcher, BasicAuthCredentialsSupplier basicAuthCredentialsSupplier,
													   OAuth2ClientDetailsService oAuth2ClientDetailsService,
													   AppmarketEventClient appmarketEventClient) {
		return new AppmarketEventService(appmarketEventClient, credentialsSupplier, oAuth2ClientDetailsService, eventDispatcher, basicAuthCredentialsSupplier);
	}

	@Bean
	public AppmarketEventController appmarketEventController(AppmarketEventService appmarketEventService, OAuthKeyExtractor oauthKeyExtractor, BasicAuthUserExtractor basicAuthUserExtractor) {
		return new AppmarketEventController(appmarketEventService, oauthKeyExtractor, basicAuthUserExtractor);
	}

	@Bean
	public AppmarketMigrationService appmarketMigrationService(CustomerAccountValidationHandler customerAccountValidationHandler, SubscriptionValidationHandler subscriptionValidationHandler, SubscriptionMigrationHandler subscriptionMigrationHandler) {
		return new AppmarketMigrationService(customerAccountValidationHandler, subscriptionValidationHandler, subscriptionMigrationHandler);
	}

	@Bean
	public AppmarketMigrationController appmarketMigrationController(AppmarketMigrationService appmarketMigrationService) {
		return new AppmarketMigrationController(appmarketMigrationService);
	}

	@Bean
	public RestTemplateFactory appMarketRestTemplateFactory() {
		return new DefaultRestTemplateFactoryImpl(new AppmarketEventClientExceptionHandler());
	}

	@Bean
	public RestTemplateFactory billingRestTemplateFactory() {
		return new ReportUsageRestTemplateFactoryImpl();
	}

	@Bean
	public AppmarketEventClientExceptionHandler appmarketEventConsumerExceptionHandler() {
		return new AppmarketEventClientExceptionHandler();
	}

	@Bean
	public DeveloperExceptionHandler developerExceptionHandler() {
		return new DeveloperExceptionHandler();
	}
}
