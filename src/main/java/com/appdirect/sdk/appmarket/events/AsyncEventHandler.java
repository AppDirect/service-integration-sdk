package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.APIResult.async;
import static com.appdirect.sdk.appmarket.events.APIResult.failure;
import static com.appdirect.sdk.appmarket.events.ErrorCode.UNKNOWN_ERROR;

import java.util.concurrent.Executor;

import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.exception.DeveloperServiceException;

@Slf4j
public class AsyncEventHandler {
	private final Executor executor;
	private final AppmarketEventClient appmarketEventClient;

	public AsyncEventHandler(Executor executor, AppmarketEventClient appmarketEventClient) {
		this.executor = executor;
		this.appmarketEventClient = appmarketEventClient;
	}

	public APIResult handle(SDKEventHandler eventHandler, String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		executor.execute(() -> {
			APIResult result;
			try {
				result = eventHandler.handle(consumerKeyUsedByTheRequest, eventInfo);
			} catch (DeveloperServiceException e) {
				log.error("Service returned an error for eventId={}, result={}", eventInfo.getId(), e.getResult());
				result = e.getResult();
			} catch (Exception e) {
				log.error("Exception while attempting to process an event. eventId={}", eventInfo.getId(), e);
				result = failure(UNKNOWN_ERROR, e.getMessage());
			}

			if (result != null) {
				appmarketEventClient.resolve(eventInfo, result, consumerKeyUsedByTheRequest);
			}
		});
		return async("Event has been accepted by the connector. It will be processed soon.");
	}
}
