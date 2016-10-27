package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.EventType;

 /**
 * Defines a handler for incoming marketplace events of a supported {@link EventType}
 */
public interface AppmarketEventProcessor {
	/**
	 * Indicates if an event is supported by the processor.
	 */
	boolean supports(EventType eventType);

	/**
	 * Process Event
	 */
	APIResult process(EventInfo event, String baseAppmarketUrl);
}
