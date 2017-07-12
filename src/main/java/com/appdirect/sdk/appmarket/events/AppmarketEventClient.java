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

import static com.appdirect.sdk.utils.EventIdExtractor.extractId;
import static java.lang.String.format;

import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.saml.ServiceProviderInformation;
import com.appdirect.sdk.web.RestOperationsFactory;

/**
 * This class defines method for performing HTTP requests against an AppMarket instance
 */
@Slf4j
public class AppmarketEventClient {

	private final RestOperationsFactory restClientFactory;
	private final DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;

	AppmarketEventClient(RestOperationsFactory restClientFactory, DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier) {
		this.restClientFactory = restClientFactory;
		this.credentialsSupplier = credentialsSupplier;
	}

	/**
	 * Perform "signed fetch" in order to retrieve the payload of an event sent to the connector from the AppMarket
	 *
	 * @param url         from which we can fetch the event payload
	 * @param credentials the credentials used to sign the request
	 * @return an {@link EventInfo} instance representing the retrieved payload
	 */
	EventInfo fetchEvent(String url, Credentials credentials) {
		log.debug("Consuming event from url={}", url);
		EventInfo fetchedEvent = restClientFactory
				.restOperationsForProfile(credentials.developerKey, credentials.developerSecret)
				.getForObject(url, EventInfo.class);
		fetchedEvent.setId(extractId(url));
		return fetchedEvent;
	}

	/**
	 * Send an "event resolved" notification for an asynchronous event. It serves to notify the
	 * AppMarket that the processing of a given event by the connector has been completed
	 *
	 * @param baseAppmarketUrl host on which the marketplace is running
	 * @param eventToken          the id of the event we would like to resolve
	 * @param result           represents the event processing result sent to the AppMarket. It would indicate if the event
	 *                         processing has been successful or not.
	 * @param key              the client key used to sign the resolve request
	 */
	public void resolve(String baseAppmarketUrl, String eventToken, APIResult result, String key) {
		String url = eventResolutionEndpoint(baseAppmarketUrl, eventToken);
		String secret = credentialsSupplier.getConsumerCredentials(key).developerSecret;

		restClientFactory.restOperationsForProfile(key, secret).postForObject(url, result, String.class);
		log.info("Resolved event with eventToken={} with apiResult={}", eventToken, result);
	}

	public ServiceProviderInformation resolveSamlIdp(String url, String key) {
		String secret = credentialsSupplier.getConsumerCredentials(key).developerSecret;

		return restClientFactory.restOperationsForProfile(key, secret).getForObject(url, ServiceProviderInformation.class);
	}

	private String eventResolutionEndpoint(String baseAppmarketUrl, String eventToken) {
		return format("%s/api/integration/v1/events/%s/result", baseAppmarketUrl, eventToken);
	}
}
