package com.appdirect.sdk.marketplace;

import com.appdirect.sdk.marketplace.api.type.EventType;
import com.appdirect.sdk.marketplace.api.vo.APIResult;
import com.appdirect.sdk.marketplace.api.vo.EventInfo;

public interface MarketplaceEventProcessor {
	/**
	 * Indicates if an event is supported by the processor.
	 */
	boolean supports(EventType eventType);

	/**
	 * Process Event
	 */
	APIResult process(EventInfo event, String baseMarketplaceUrl);
}
