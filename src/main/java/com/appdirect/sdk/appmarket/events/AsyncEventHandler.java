package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.APIResult.async;

import java.util.concurrent.Executor;

public class AsyncEventHandler {
	private final Executor executor;

	public AsyncEventHandler(Executor executor) {
		this.executor = executor;
	}

	public APIResult handle(SDKEventHandler eventHandler, String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		executor.execute(() -> {
			APIResult result = eventHandler.handle(consumerKeyUsedByTheRequest, eventInfo);
			// TODO: resolve the event on the appmarket
		});
		return async("Event has been accepted by the connector. It will be processed soon.");
	}
}
