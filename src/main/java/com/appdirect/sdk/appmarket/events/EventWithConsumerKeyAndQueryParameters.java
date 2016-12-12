package com.appdirect.sdk.appmarket.events;

import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * abstract event type that offers the consumer key used by the request publishing the event
 * and the map of query parameters received with this event.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public abstract class EventWithConsumerKeyAndQueryParameters {
	private final String consumerKeyUsedByTheRequest;
	private final Map<String, String[]> queryParameters;

	public Map<String, String[]> getQueryParameters() {
		return new HashMap<>(queryParameters);
	}
}
