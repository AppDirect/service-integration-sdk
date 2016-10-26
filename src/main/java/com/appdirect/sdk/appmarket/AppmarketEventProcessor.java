package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.type.EventType;
import com.appdirect.sdk.appmarket.api.vo.APIResult;
import com.appdirect.sdk.appmarket.api.vo.EventInfo;

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
