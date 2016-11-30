package com.appdirect.sdk.appmarket.events;

import static java.lang.String.format;

import java.net.URI;

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
		return restClientFactory.restOperationsForProfile(key, secret).getForObject(url, EventInfo.class);
	}

	public void resolve(EventInfo eventToResolve, APIResult result, String key, String eventUrl) {
		String url = eventResolutionEndpoint(eventToResolve, eventUrl);
		String secret = credentialsSupplier.getConsumerCredentials(key).developerSecret;

		restClientFactory.restOperationsForProfile(key, secret).postForObject(url, result, String.class);
	}

	private String eventResolutionEndpoint(EventInfo eventToResolve, String eventUrl) {
		String path = URI.create(eventUrl).getPath();
		String eventId = path.substring(path.lastIndexOf("/") + 1);

		String appmarketUrl = eventToResolve.getMarketplace().getBaseUrl();
		return format("%s/api/integration/v1/events/%s/result", appmarketUrl, eventId);
	}
}
