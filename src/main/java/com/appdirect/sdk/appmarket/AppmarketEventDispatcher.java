package com.appdirect.sdk.appmarket;

import static java.lang.String.format;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import com.appdirect.sdk.appmarket.alt.SDKEventHandler;
import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.ErrorCode;
import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.EventType;

@RequiredArgsConstructor
public class AppmarketEventDispatcher {
	Map<EventType, SDKEventHandler<?>> processors;

	public APIResult dispatchAndHandle(EventInfo eventInfo) {
		SDKEventHandler<?> developerEventHandlerWrapper = processors.getOrDefault(eventInfo.getType(), unknownEventHandler());
		return developerEventHandlerWrapper.handle(eventInfo);
	}

	private SDKEventHandler<Object> unknownEventHandler() {
		return (e) -> new APIResult(ErrorCode.CONFIGURATION_ERROR, format("Unsupported event type %s", "d"));
	}
}
