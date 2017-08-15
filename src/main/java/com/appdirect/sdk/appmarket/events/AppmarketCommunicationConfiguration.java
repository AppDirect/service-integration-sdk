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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.migration.AppmarketMigrationController;
import com.appdirect.sdk.appmarket.migration.AppmarketMigrationService;
import com.appdirect.sdk.appmarket.migration.CustomerAccountValidationHandler;
import com.appdirect.sdk.appmarket.migration.SubscriptionValidationHandler;
import com.appdirect.sdk.web.exception.AppmarketEventClientExceptionHandler;
import com.appdirect.sdk.web.oauth.DefaultRestTemplateFactoryImpl;
import com.appdirect.sdk.web.oauth.OAuthKeyExtractor;
import com.appdirect.sdk.web.oauth.ReportUsageRestTemplateFactoryImpl;
import com.appdirect.sdk.web.oauth.RestTemplateFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppmarketCommunicationConfiguration {

	@Bean
	public AppmarketBillingClient appmarketBillingReporter(DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier,
																												 @Qualifier("sdkInternalJsonMapper") ObjectMapper mapper) {
		return new AppmarketBillingClient(billingRestTemplateFactory(), credentialsSupplier, mapper);
	}

	@Bean
	public AppmarketEventClient appmarketEventFetcher(DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier,
																										@Qualifier("sdkInternalJsonMapper") ObjectMapper mapper) {
		return new AppmarketEventClient(restTemplateFactory(), credentialsSupplier, mapper);
	}

	@Bean
	public AppmarketEventService appmarketEventService(DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier,
																										 AppmarketEventDispatcher eventDispatcher,
																										 AppmarketEventClient appmarketEventClient) {
		return new AppmarketEventService(appmarketEventClient, credentialsSupplier, eventDispatcher);
	}

	@Bean
	public AppmarketEventController appmarketEventController(AppmarketEventService appmarketEventService, OAuthKeyExtractor oauthKeyExtractor) {
		return new AppmarketEventController(appmarketEventService, oauthKeyExtractor);
	}

	@Bean
	public AppmarketMigrationService appmarketMigrationService(CustomerAccountValidationHandler customerAccountValidationHandler, SubscriptionValidationHandler subscriptionValidationHandler) {
		return new AppmarketMigrationService(customerAccountValidationHandler, subscriptionValidationHandler);
	}

	@Bean
	public AppmarketMigrationController appmarketMigrationController(AppmarketMigrationService appmarketMigrationService) {
		return new AppmarketMigrationController(appmarketMigrationService);
	}

	@Bean
	public RestTemplateFactory restTemplateFactory() {
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
