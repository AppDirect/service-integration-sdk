package com.appdirect.sdk.appmarket;

import static com.appdirect.sdk.appmarket.api.ErrorCode.CONFIGURATION_ERROR;
import static java.lang.String.format;

import java.util.Map;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.EventType;

public class AppmarketEventDispatcher {
	private final Map<EventType, SDKEventHandler> handlers;

	public AppmarketEventDispatcher(Map<EventType, SDKEventHandler> handlers) {
		this.handlers = handlers;
	}

	public APIResult dispatchAndHandle(EventInfo eventInfo) {
		SDKEventHandler developerEventHandlerWrapper = handlers.getOrDefault(eventInfo.getType(), unknownEventHandler());
		return developerEventHandlerWrapper.handle(eventInfo);
	}

	private SDKEventHandler unknownEventHandler() {
		return event -> new APIResult(CONFIGURATION_ERROR, format("Unsupported event type %s", event.getType()));
	}
}
