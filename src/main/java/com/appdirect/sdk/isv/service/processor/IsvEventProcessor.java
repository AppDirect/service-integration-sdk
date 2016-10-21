package com.appdirect.sdk.isv.service.processor;

import com.appdirect.sdk.isv.api.model.type.EventType;
import com.appdirect.sdk.isv.api.model.vo.APIResult;
import com.appdirect.sdk.isv.api.model.vo.EventInfo;

public interface IsvEventProcessor {
	/**
	 * Indicates if an event is supported by the processor.
	 */
	boolean supports(EventType eventType);

	/**
	 * Process Event
	 */
	APIResult process(EventInfo event, String baseMarketplaceUrl);
}
