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

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;

import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.web.oauth.RestTemplateFactory;

/**
 * This class will notifies the AppMarket about the domain verification status.
 */
@Slf4j
public class DomainVerificationNotificationClient {
	private final RestTemplateFactory restTemplateFactory;
	private final DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;

	public DomainVerificationNotificationClient(RestTemplateFactory restTemplateFactory, DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier) {
		this.restTemplateFactory = restTemplateFactory;
		this.credentialsSupplier = credentialsSupplier;
	}

	public void resolveDomainVerification(String callbackUrl, String key, boolean isVerified) {
		String secret = credentialsSupplier.getConsumerCredentials(key).developerSecret;
		ResponseEntity<String> result = restTemplateFactory.getOAuthRestTemplate(key, secret).postForEntity(callbackUrl, new DomainVerificationStatus(isVerified), String.class);
		log.info("Domain verification callbackUrl={} called with status={}", callbackUrl, result.getStatusCodeValue());
	}
}
