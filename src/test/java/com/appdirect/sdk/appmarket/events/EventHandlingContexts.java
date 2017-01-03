package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.support.QueryParameters.oneQueryParam;

import java.util.Map;

/**
 * Helper class to instantiate <code>EventHandlingContext</code>s.
 * Needs to be in this package because class is package-private.
 */
public class EventHandlingContexts {
	private EventHandlingContexts() {

	}

	public static EventHandlingContext defaultEventContext() {
		return eventContext("some-key");
	}

	public static EventHandlingContext eventContext(String consumerKey) {
		return eventContext(consumerKey, oneQueryParam());
	}

	public static EventHandlingContext eventContext(String consumerKey, Map<String, String[]> queryParam) {
		return new EventHandlingContext(consumerKey, queryParam);
	}
}
