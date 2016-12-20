package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.EventFlag.DEVELOPMENT;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
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
	/**
	 * Returns the consumer key that was used by the appmarket to publish this event.
	 * You can use this to determine which product is the originator of this event.
	 */
	private final String consumerKeyUsedByTheRequest;
	private final Map<String, String[]> queryParameters;
	@Getter(AccessLevel.NONE)
	private final EventFlag flag;

	/**
	 * Returns the query parameters that were passed to the endpoint when this event was received.
	 * i.e. calling <code>/processEvent?eventUrl=some-url&themeColor=yellow&themeColor=red</code> would yield
	 * a map with 2 entries: <code>eventUrl=[some-url]</code> and <code>themeColor=[yellow, red]</code>.
	 *
	 * @return an unmodifiable view of the query parameters map.
	 */
	public Map<String, String[]> getQueryParameters() {
		return new HashMap<>(queryParameters);
	}

	/**
	 * Returns whether this event is a development event or not. If it is a development event, implementors
	 * should not make any permanent changes to external systems (billing, emails, etc.) Development events are
	 * sent as part of the INTEGRATION REPORT feature of the marketplace.
	 *
	 * @return <code>true</code> if the event is a development event; <code>false</code> otherwise.
	 */
	public boolean isDevelopment() {
		return flag == DEVELOPMENT;
	}
}
