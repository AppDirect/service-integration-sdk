package com.appdirect.sdk.web.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MarketplaceEventConsumerExceptionHandler extends AbstractMarketplaceExceptionHandler {
	public MarketplaceEventConsumerExceptionHandler() {
		super("fetch event", log);
	}
}
