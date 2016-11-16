package com.appdirect.sdk.web;

import static com.appdirect.sdk.appmarket.api.ErrorCode.UNKNOWN_ERROR;
import static java.lang.String.format;

import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.appmarket.AppmarketEventDispatcher;
import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventFlag;
import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.exception.DeveloperServiceException;

@Slf4j
public class AppmarketEventService {
	private final AppmarketEventFetcher appmarketEventFetcher;
	private final DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;
	private final AppmarketEventDispatcher dispatcher;

	public AppmarketEventService(AppmarketEventFetcher appmarketEventFetcher,
								 DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier,
								 AppmarketEventDispatcher dispatcher) {
		this.appmarketEventFetcher = appmarketEventFetcher;
		this.credentialsSupplier = credentialsSupplier;
		this.dispatcher = dispatcher;
	}

	APIResult processEvent(String eventUrl, String keyUsedToSignRequest) {
		log.info("processing event for eventUrl={}", eventUrl);
		try {
			EventInfo event = fetchEvent(eventUrl, keyUsedToSignRequest);
			if (event.getFlag() == EventFlag.STATELESS) {
				return new APIResult(true, "success response to stateless event.");
			}
			return dispatcher.dispatchAndHandle(keyUsedToSignRequest, event);
		} catch (DeveloperServiceException e) {
			log.error("Service returned an error for eventUrl={}, result={}", eventUrl, e.getResult());
			throw e;
		} catch (RuntimeException e) {
			log.error("Exception while attempting to process an event. eventUrl={}", eventUrl, e);
			throw new DeveloperServiceException(UNKNOWN_ERROR, format("Failed to process event. eventUrl=%s", eventUrl));
		}
	}

	private EventInfo fetchEvent(String url, String keyUsedToSignRequest) {
		Credentials credentials = credentialsSupplier.getConsumerCredentials(keyUsedToSignRequest);
		EventInfo event = appmarketEventFetcher.fetchEvent(url, credentials.developerKey, credentials.developerSecret);
		log.info("Successfully retrieved event={}", event);
		return event;
	}
}
