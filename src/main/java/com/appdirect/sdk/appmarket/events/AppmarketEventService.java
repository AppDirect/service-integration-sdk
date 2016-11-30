package com.appdirect.sdk.appmarket.events;

import static java.lang.String.format;

import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.exception.DeveloperServiceException;

@Slf4j
class AppmarketEventService {
	private final AppmarketEventClient appmarketEventClient;
	private final DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;
	private final AppmarketEventDispatcher dispatcher;

	AppmarketEventService(AppmarketEventClient appmarketEventClient,
						  DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier,
						  AppmarketEventDispatcher dispatcher) {
		this.appmarketEventClient = appmarketEventClient;
		this.credentialsSupplier = credentialsSupplier;
		this.dispatcher = dispatcher;
	}

	APIResult processEvent(String eventUrl, String keyUsedToSignRequest) {
		log.info("processing event for eventUrl={}", eventUrl);
		try {
			EventInfo event = fetchEvent(eventUrl, keyUsedToSignRequest);
			if (event.getFlag() == EventFlag.STATELESS) {
				return APIResult.success("success response to stateless event.");
			}
			return dispatcher.dispatchAndHandle(keyUsedToSignRequest, event);
		} catch (DeveloperServiceException e) {
			log.error("Service returned an error for eventUrl={}, result={}", eventUrl, e.getResult());
			throw e;
		} catch (RuntimeException e) {
			log.error("Exception while attempting to process an event. eventUrl={}", eventUrl, e);
			throw new DeveloperServiceException(ErrorCode.UNKNOWN_ERROR, format("Failed to process event. eventUrl=%s", eventUrl));
		}
	}

	private EventInfo fetchEvent(String url, String keyUsedToSignRequest) {
		Credentials credentials = credentialsSupplier.getConsumerCredentials(keyUsedToSignRequest);
		EventInfo event = appmarketEventClient.fetchEvent(url, credentials.developerKey, credentials.developerSecret);
		log.info("Successfully retrieved event={}", event);
		return event;
	}
}
