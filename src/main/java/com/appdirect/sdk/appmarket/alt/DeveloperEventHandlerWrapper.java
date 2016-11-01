package com.appdirect.sdk.appmarket.alt;

import lombok.RequiredArgsConstructor;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventInfo;

@RequiredArgsConstructor
public class DeveloperEventHandlerWrapper<T> implements SDKEventHandler<T> {
	private final EventParser<T> parser;
	private final DeveloperEventHandler<T> eventHandler;

	@Override
	public APIResult handle(EventInfo event) {
		T parsedEvent = parser.parse(event);
		return eventHandler.handle(parsedEvent);
	}
}
