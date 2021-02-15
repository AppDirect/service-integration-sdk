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

package com.appdirect.sdk.appmarket.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.web.exception.AppmarketEventClientExceptionHandler;
import com.appdirect.sdk.web.oauth.DefaultRestTemplateFactoryImpl;
import com.appdirect.sdk.web.oauth.OAuth2ClientDetailsService;
import com.appdirect.sdk.web.oauth.OAuthKeyExtractor;
import com.appdirect.sdk.web.oauth.RestTemplateFactory;

/**
 * Provides the dependencies necessary for a connector for domain linking functionality.
 * This configuration has to be explicitly imported by the SDK client, the same as the
 * {@link com.appdirect.sdk.ConnectorSdkConfiguration} class.
 * This configuration should be imported if the SDK client required to associate domains to the subscription.
 */
@Configuration
public abstract class DomainDnsOwnershipVerificationConfiguration {
	@Bean
	public abstract DomainDnsVerificationInfoHandler domainDnsVerificationInfoHandler();

	@Bean
	public abstract DomainServiceConfigurationHandler domainServiceConfigurationHandler();

	@Bean
	public abstract DomainOwnershipVerificationHandler domainOwnershipVerificationHandler(DomainVerificationNotificationClient domainVerificationNotificationClient);
	@Bean
	public abstract DomainAdditionHandler domainAdditionHandler();

	@Bean
	public abstract DomainRemovalHandler domainRemovalHandler();

	@Bean
	public DomainOwnershipController domainDnsVerificationInfoController(DomainDnsVerificationInfoHandler domainDnsVerificationInfoHandler,
																																			 DomainServiceConfigurationHandler domainServiceConfigurationHandler,
																																			 DomainOwnershipVerificationHandler domainOwnershipVerificationHandler,
																																			 DomainAdditionHandler domainAdditionHandler,
																																			 DomainRemovalHandler domainRemovalHandler,
																																			 OAuthKeyExtractor keyExtractor) {
		return new DomainOwnershipController(domainDnsVerificationInfoHandler,
			domainServiceConfigurationHandler,
			domainOwnershipVerificationHandler,
			domainAdditionHandler,
			domainRemovalHandler,
			keyExtractor);
	}

	@Bean
	public DomainVerificationNotificationClient domainVerificationNotificationClient(DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier) {
		return new DomainVerificationNotificationClient(domainRestTemplateFactory(), credentialsSupplier);
	}

	@Bean
	public RestTemplateFactory domainRestTemplateFactory() {
		return new DefaultRestTemplateFactoryImpl(new AppmarketEventClientExceptionHandler());
	}
}
