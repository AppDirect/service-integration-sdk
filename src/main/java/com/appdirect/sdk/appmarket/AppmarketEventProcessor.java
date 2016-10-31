package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.Event;
import com.appdirect.sdk.appmarket.api.EventType;

/**
 * Defines a handler for incoming marketplace events of a supported {@link EventType}
 *
 * @param <T> the type of the event handled by this processor
 */
public interface AppmarketEventProcessor<T extends Event> {
	/**
	 * Indicates if an event is supported by the processor.
	 */
	boolean supports(EventType eventType);

	/**
	 * Process Event
	 */
	APIResult process(T event, String baseAppmarketUrl);
}
