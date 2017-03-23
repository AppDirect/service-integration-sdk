package com.appdirect.sdk.utils;

import java.net.URI;

public final class EventIdExtractor {

	private EventIdExtractor() {
	}

	/**
	 * Extracts the id of the incoming AppMarket event from the eventUrl parameter passed to the connector.
	 * Check the AppDirect documentation for more details regarding the eventUrl parameter
	 *
	 * @param eventUrl a url from which the connector can fetch the payload of an incoming subscription event.
	 * @return the id of the incoming event
	 * @see <a href="http://google.com">https://docs.appdirect.com/developer/distribution/event-notifications/subscription-events</a>
	 */
	public static String extractId(String eventUrl) {
		String path = URI.create(eventUrl).getPath();
		return path.substring(path.lastIndexOf("/") + 1);
	}
}
