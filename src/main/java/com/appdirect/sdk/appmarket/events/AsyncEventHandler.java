package com.appdirect.sdk.appmarket.events;

public class AsyncEventHandler {
	public APIResult handle(SDKEventHandler eventHandler, String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		return APIResult.failure(ErrorCode.CONFIGURATION_ERROR, "not implemented!");
	}
}
