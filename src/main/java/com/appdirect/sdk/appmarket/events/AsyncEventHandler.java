package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.APIResult.async;

import java.util.concurrent.Executor;

public class AsyncEventHandler {
	private final Executor executor;
	private final AppmarketEventClient appmarketEventClient;

	public AsyncEventHandler(Executor executor, AppmarketEventClient appmarketEventClient) {
		this.executor = executor;
		this.appmarketEventClient = appmarketEventClient;
	}

	public APIResult handle(SDKEventHandler eventHandler, String consumerKeyUsedByTheRequest, EventInfo eventInfo, String eventUrl) {
		executor.execute(() -> {
			APIResult result = eventHandler.handle(consumerKeyUsedByTheRequest, eventInfo);
			if (result != null) {
				appmarketEventClient.resolve(eventInfo, result, consumerKeyUsedByTheRequest);
			}
		});
		return async("Event has been accepted by the connector. It will be processed soon.");
	}
}
