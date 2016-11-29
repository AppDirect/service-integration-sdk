package com.appdirect.sdk.appmarket.events;

import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.web.RestOperationsFactory;

@Slf4j
class AppmarketEventClient {

	private final RestOperationsFactory restClientFactory;

	AppmarketEventClient(RestOperationsFactory restClientFactory) {
		this.restClientFactory = restClientFactory;
	}

	EventInfo fetchEvent(String url, String key, String secret) {
		log.debug("Consuming event from url={}", url);
		return restClientFactory.restOperationsForProfile(key, secret).getForObject(url, EventInfo.class);
	}

	public void resolve(EventInfo eventToResolve, APIResult result) {
		// TODO: implement!
	}
}
