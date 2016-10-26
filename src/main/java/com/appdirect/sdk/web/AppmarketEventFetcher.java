package com.appdirect.sdk.web;

import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.appmarket.api.vo.EventInfo;

@Slf4j
public class AppmarketEventFetcher {

	private final RestOperationsFactory restClientFactory;

	public AppmarketEventFetcher(RestOperationsFactory restClientFactory) {
		this.restClientFactory = restClientFactory;
	}

	public EventInfo fetchEvent(String url, String key, String secret) {
		log.debug("Consuming event from url={}", url);
		return restClientFactory.restOperationsForProfile(key, secret).getForObject(url, EventInfo.class);
	}

}
