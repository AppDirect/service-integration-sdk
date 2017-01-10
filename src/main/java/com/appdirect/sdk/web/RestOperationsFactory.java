package com.appdirect.sdk.web;

import org.springframework.web.client.RestTemplate;

import com.appdirect.sdk.web.exception.AppmarketEventClientExceptionHandler;
import com.appdirect.sdk.web.oauth.OAuthSignedClientHttpRequestFactory;

public class RestOperationsFactory {
	private final AppmarketEventClientExceptionHandler errorHandler;

	public RestOperationsFactory(AppmarketEventClientExceptionHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	public RestTemplate restOperationsForProfile(String key, String secret) {
		RestTemplate restTemplate = new RestTemplate(new OAuthSignedClientHttpRequestFactory(key, secret));
		restTemplate.setErrorHandler(errorHandler);
		return restTemplate;
	}
}
