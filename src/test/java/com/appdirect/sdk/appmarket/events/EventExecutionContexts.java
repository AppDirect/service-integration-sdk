package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.support.QueryParameters.oneQueryParam;

import java.util.Map;

/**
 * Helper class to instantiate <code>EventExecutionContext</code>s.
 * Needs to be in this package because class is package-private.
 */
public class EventExecutionContexts {
	public static EventExecutionContext defaultEventContext() {
		return eventContext("some-key");
	}

	public static EventExecutionContext eventContext(String consumerKey) {
		return eventContext(consumerKey, oneQueryParam());
	}

	public static EventExecutionContext eventContext(String consumerKey, Map<String, String[]> queryParam) {
		return new EventExecutionContext(consumerKey, queryParam);
	}
}
