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

import static org.springframework.http.MediaType.APPLICATION_JSON;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.exception.ReportUsageException;
import com.appdirect.sdk.web.oauth.RestTemplateFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is responsible of calling the Billing API REST endpoint against an AppMarket instance
 */
@Slf4j
public class AppmarketBillingClient {
	private final RestTemplateFactory restTemplateFactory;
	private final DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;
	private final ObjectMapper jsonMapper;

	AppmarketBillingClient(RestTemplateFactory restTemplateFactory, DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier, ObjectMapper jsonMapper) {
		this.restTemplateFactory = restTemplateFactory;
		this.credentialsSupplier = credentialsSupplier;
		this.jsonMapper = jsonMapper;
	}

	/**
	 * Calls the Billing API REST endpoint against an AppMarket instance to bill usage
	 *
	 * @param baseAppmarketUrl to which the POST is sent
	 * @param key              to sign the request
	 * @param usage            to be billed
	 *
	 * @return an {@link APIResult} instance representing the marketplace response
	 * <p>
	 * throws an {@link ReportUsageException} to the client with an error code and a status:
	 * Configuration Error: The JSON sent to the marketplace had wrong parameters.
	 * User Not Found:      One of the Usage parameters was not found. Probably the accountIdentifier.
	 * Unknown Error:       Error on marketplace side.
	 */
	@SneakyThrows
	public APIResult billUsage(String baseAppmarketUrl, String key, Usage usage) {

		String url = UriComponentsBuilder.fromHttpUrl(baseAppmarketUrl)
				.pathSegment("api", "integration", "v1", "billing", "usage")
				.build().toString();
		String secret = credentialsSupplier.getConsumerCredentials(key).developerSecret;

		final RestTemplate restTemplate = restTemplateFactory.getOAuthRestTemplate(key, secret);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(APPLICATION_JSON);

		final HttpEntity<String> requestEntity = new HttpEntity<>(jsonMapper.writeValueAsString(usage), requestHeaders);

		return restTemplate.postForObject(url, requestEntity, APIResult.class);
	}
}
