package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventInfo;

class ParseAndHandleWrapper<T> implements SDKEventHandler<T> {
	private final EventParser<T> parser;
	private final AppmarketEventHandler<T> eventHandler;

	ParseAndHandleWrapper(EventParser<T> parser, AppmarketEventHandler<T> eventHandler) {
		this.parser = parser;
		this.eventHandler = eventHandler;
	}

	@Override
	public APIResult handle(EventInfo event) {
		T parsedEvent = parser.parse(event);
		return eventHandler.handle(parsedEvent);
	}
}
