package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.utils.EventIdExtractor.extractId;
import static java.lang.String.format;

import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.web.RestOperationsFactory;

@Slf4j
class AppmarketEventClient {

	private final RestOperationsFactory restClientFactory;
	private final DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;

	AppmarketEventClient(RestOperationsFactory restClientFactory, DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier) {
		this.restClientFactory = restClientFactory;
		this.credentialsSupplier = credentialsSupplier;
	}

	EventInfo fetchEvent(String url, String key, String secret) {
		log.debug("Consuming event from url={}", url);
		EventInfo fetchedEvent = restClientFactory.restOperationsForProfile(key, secret).getForObject(url, EventInfo.class);
		fetchedEvent.setId(extractId(url));
		return fetchedEvent;
	}

	public void resolve(EventInfo eventToResolve, APIResult result, String key) {
		String url = eventResolutionEndpoint(eventToResolve);
		String secret = credentialsSupplier.getConsumerCredentials(key).developerSecret;

		restClientFactory.restOperationsForProfile(key, secret).postForObject(url, result, String.class);
	}

	private String eventResolutionEndpoint(EventInfo eventToResolve) {
		String appmarketUrl = eventToResolve.getMarketplace().getBaseUrl();
		return format("%s/api/integration/v1/events/%s/result", appmarketUrl, eventToResolve.getId());
	}
}
