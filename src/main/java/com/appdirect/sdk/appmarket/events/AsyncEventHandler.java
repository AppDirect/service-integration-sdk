package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.APIResult.asyncEventResult;
import static com.appdirect.sdk.appmarket.events.APIResult.failure;
import static com.appdirect.sdk.appmarket.events.ErrorCode.UNKNOWN_ERROR;
import static java.lang.String.format;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appdirect.sdk.exception.DeveloperServiceException;

class AsyncEventHandler {
	private final Logger log;
	private final Executor executor;
	private final AppmarketEventClient appmarketEventClient;

	AsyncEventHandler(Executor executor, AppmarketEventClient appmarketEventClient) {
		this(executor, appmarketEventClient, LoggerFactory.getLogger(AsyncEventHandler.class));
	}

	AsyncEventHandler(Executor executor, AppmarketEventClient appmarketEventClient, Logger log) {
		this.executor = executor;
		this.appmarketEventClient = appmarketEventClient;
		this.log = log;
	}

	APIResult handle(SDKEventHandler eventHandler, EventInfo eventInfo, EventExecutionContext eventContext) {
		executor.execute(() -> {
			APIResult result;
			try {
				result = eventHandler.handle(eventInfo, eventContext);
			} catch (DeveloperServiceException e) {
				log.error("Exception while attempting to process an event. eventId={}", eventInfo.getId(), e);
				result = e.getResult();
			} catch (Exception e) {
				log.error("Exception while attempting to process an event. eventId={}", eventInfo.getId(), e);
				result = failure(UNKNOWN_ERROR, e.getMessage());
			}

			if (result != null) {
				appmarketEventClient.resolve(eventInfo.getMarketplace().getBaseUrl(), eventInfo.getId(), result, eventContext.getConsumerKeyUsedByTheRequest());
			}
		});
		return asyncEventResult(
			format("Event with eventId=%s has been accepted by the connector. It will be processed soon.", eventInfo.getId())
		);
	}
}
