package com.appdirect.sdk;

import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.web.RestOperationsFactory;

@Slf4j
class AppmarketEventFetcher {

	private final RestOperationsFactory restClientFactory;

	AppmarketEventFetcher(RestOperationsFactory restClientFactory) {
		this.restClientFactory = restClientFactory;
	}

	EventInfo fetchEvent(String url, String key, String secret) {
		log.debug("Consuming event from url={}", url);
		return restClientFactory.restOperationsForProfile(key, secret).getForObject(url, EventInfo.class);
	}

}
