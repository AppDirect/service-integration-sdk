package com.appdirect.sdk.web;

import static com.appdirect.sdk.appmarket.api.ErrorCode.UNKNOWN_ERROR;
import static java.lang.String.format;

import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.appmarket.AppmarketEventDispatcher;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventFlag;
import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.exception.DeveloperServiceException;

@Slf4j
public class AppmarketEventService {
	private final AppmarketEventFetcher appmarketEventFetcher;
	private final Supplier<DeveloperSpecificAppmarketCredentials> credentialsSupplier;
	private final AppmarketEventDispatcher dispatcher;

	public AppmarketEventService(AppmarketEventFetcher appmarketEventFetcher,
								 DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier,
								 AppmarketEventDispatcher dispatcher) {
		this.appmarketEventFetcher = appmarketEventFetcher;
		this.credentialsSupplier = credentialsSupplier;
		this.dispatcher = dispatcher;
	}

	APIResult processEvent(String url) {
		log.info("processing event for eventUrl={}", url);
		try {
			EventInfo event = fetchEvent(url);
			if (event.getFlag() == EventFlag.STATELESS) {
				return new APIResult(true, "success response to stateless event.");
			}
			return dispatcher.dispatchAndHandle(event);
		} catch (DeveloperServiceException e) {
			log.error("Service returned an error for url={}, result={}", url, e.getResult());
			throw e;
		} catch (RuntimeException e) {
			log.error("Exception while attempting to process an event. eventUrl={}", url, e);
			throw new DeveloperServiceException(UNKNOWN_ERROR, format("Failed to process event. url=%s", url));
		}
	}

	private EventInfo fetchEvent(String url) {
		DeveloperSpecificAppmarketCredentials credentials = credentialsSupplier.get();
		EventInfo event = appmarketEventFetcher.fetchEvent(url, credentials.getDeveloperKey(), credentials.getDeveloperSecret());
		log.info("Successfully retrieved event={}", event);
		return event;
	}
}
