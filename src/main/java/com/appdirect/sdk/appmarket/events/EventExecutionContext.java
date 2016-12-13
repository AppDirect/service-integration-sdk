package com.appdirect.sdk.appmarket.events;

import static java.util.Collections.unmodifiableMap;

import java.util.Map;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
class EventExecutionContext {
	private final String consumerKeyUsedByTheRequest;
	private final Map<String, String[]> queryParameters;

	EventExecutionContext(String consumerKeyUsedByTheRequest, Map<String, String[]> queryParameters) {
		this.consumerKeyUsedByTheRequest = consumerKeyUsedByTheRequest;
		this.queryParameters = queryParameters;
	}

	public String getConsumerKeyUsedByTheRequest() {
		return consumerKeyUsedByTheRequest;
	}

	public Map<String, String[]> getQueryParameters() {
		return unmodifiableMap(queryParameters);
	}
}
