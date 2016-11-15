package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventInfo;

/**
 * SDK internal - Convenience class that parses an event into a rich event and sends it to its event handler
 *
 * @param <T> the type of the rich event the parser and handler support
 */
class ParseAndHandleWrapper<T> implements SDKEventHandler {
	private final EventParser<T> parser;
	private final AppmarketEventHandler<T> eventHandler;

	ParseAndHandleWrapper(EventParser<T> parser, AppmarketEventHandler<T> eventHandler) {
		this.parser = parser;
		this.eventHandler = eventHandler;
	}

	@Override
	public APIResult handle(String consumerKeyUsedByTheRequest, EventInfo event) {
		T parsedEvent = parser.parse(consumerKeyUsedByTheRequest, event);
		return eventHandler.handle(parsedEvent);
	}
}
