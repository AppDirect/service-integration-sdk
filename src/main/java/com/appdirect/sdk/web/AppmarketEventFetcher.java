package com.appdirect.sdk.web;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.appdirect.sdk.appmarket.api.vo.EventInfo;
import com.appdirect.sdk.web.exception.AppmarketEventConsumerExceptionHandler;
import com.appdirect.sdk.web.oauth.OAuthSignedClientHttpRequestFactory;

@Slf4j
public class AppmarketEventFetcher {

	private final AppmarketEventConsumerExceptionHandler errorHandler;

	public AppmarketEventFetcher(AppmarketEventConsumerExceptionHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	private RestOperations restOperations(String key, String secret) {
		RestTemplate restTemplate = new RestTemplate(new OAuthSignedClientHttpRequestFactory(key, secret));
		restTemplate.setErrorHandler(errorHandler);
		return restTemplate;
	}

	public EventInfo fetchEvent(String url, String key, String secret) {
		log.debug("Consuming event from url={}", url);
		return restOperations(key, secret).getForObject(url, EventInfo.class);
	}
}
