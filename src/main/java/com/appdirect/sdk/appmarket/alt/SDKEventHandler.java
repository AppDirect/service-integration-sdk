package com.appdirect.sdk.appmarket.alt;

import lombok.RequiredArgsConstructor;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventInfo;

@RequiredArgsConstructor
public class SDKEventHandler<T> {
	//The @Configuration file of the SDK would define one of these for each possible event
	private final EventParser<T> parser;
	private final DeveloperEventHandler<T> eventProcessor;

	APIResult handle(EventInfo event) {
		T parsedEvent = parser.parse(event);
		return eventProcessor.handle(parsedEvent);
	}
}
