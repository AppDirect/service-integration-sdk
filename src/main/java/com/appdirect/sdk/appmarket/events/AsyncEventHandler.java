/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

	/**
	 * Handles a raw AppMarket event asynchronously.
	 * @param eventHandler contains the event handling logic for the incoming event
	 * @param eventInfo the raw AppMarket event payload
	 * @param eventContext contextual information about the event notification
	 * @return and {@link APIResult} instance representing the response to be returned to the event notification.
	 */
	APIResult handle(SDKEventHandler eventHandler, EventInfo eventInfo, EventHandlingContext eventContext) {
		executor.execute(() -> {
			APIResult result;
			try {
				result = eventHandler.handle(eventInfo, eventContext);
			} catch (DeveloperServiceException e) {
				log.error("Exception while attempting to process an event. eventToken={}", eventInfo.getId(), e);
				result = e.getResult();
			} catch (Exception e) {
				log.error("Exception while attempting to process an event. eventToken={}", eventInfo.getId(), e);
				result = failure(UNKNOWN_ERROR, e.getMessage());
			}

			if (result != null) {
				appmarketEventClient.resolve(eventInfo.getMarketplace().getBaseUrl(), eventInfo.getId(), result, eventContext.getConsumerKeyUsedByTheRequest());
			}
		});
		return asyncEventResult(
			format("Event with eventToken=%s has been accepted by the connector. It will be processed soon.", eventInfo.getId())
		);
	}
}
