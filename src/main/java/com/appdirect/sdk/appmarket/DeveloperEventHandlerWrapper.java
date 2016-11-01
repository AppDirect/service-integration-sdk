package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventInfo;

class DeveloperEventHandlerWrapper<T> implements SDKEventHandler<T> {
	private final EventParser<T> parser;
	private final DeveloperEventHandler<T> eventHandler;

	DeveloperEventHandlerWrapper(EventParser<T> parser, DeveloperEventHandler<T> eventHandler) {
		this.parser = parser;
		this.eventHandler = eventHandler;
	}

	@Override
	public APIResult handle(EventInfo event) {
		T parsedEvent = parser.parse(event);
		return eventHandler.handle(parsedEvent);
	}
}
