package com.appdirect.sdk.web;

import static com.appdirect.sdk.appmarket.api.ErrorCode.UNKNOWN_ERROR;
import static java.lang.String.format;

import java.net.URI;
import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpHost;
import org.apache.http.client.utils.URIUtils;

import com.appdirect.sdk.appmarket.AppmarketEventProcessorRegistry;
import com.appdirect.sdk.appmarket.IsvSpecificAppmarketCredentials;
import com.appdirect.sdk.appmarket.IsvSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventFlag;
import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.exception.IsvServiceException;

@Slf4j
public class AppmarketEventService {
	private final AppmarketEventFetcher appmarketEventFetcher;
	private final AppmarketEventProcessorRegistry eventProcessorRegistry;
	private final Supplier<IsvSpecificAppmarketCredentials> credentialsSupplier;

	public AppmarketEventService(AppmarketEventFetcher appmarketEventFetcher,
								 AppmarketEventProcessorRegistry eventProcessorRegistry,
								 IsvSpecificAppmarketCredentialsSupplier credentialsSupplier) {
		this.appmarketEventFetcher = appmarketEventFetcher;
		this.eventProcessorRegistry = eventProcessorRegistry;
		this.credentialsSupplier = credentialsSupplier;
	}

	APIResult processEvent(String url) {
		log.info("processing event for eventUrl={}", url);
		try {
			String baseUrl = extractBaseAppmarketUrl(url);
			EventInfo event = fetchEvent(url);
			if (event.getFlag() == EventFlag.STATELESS) {
				return new APIResult(true, "success response to stateless event.");
			}
			return process(event, baseUrl);
		} catch (IsvServiceException e) { // this is a business error, bubble it up: it's handled elsewhere.
			log.error("Service returned an error for url={}, result={}", url, e.getResult());
			throw e;
		} catch (RuntimeException e) {
			log.error("Exception while attempting to process an event. eventUrl={}", url, e);
			throw new IsvServiceException(UNKNOWN_ERROR, format("Failed to process event. url=%s", url));
		}
	}

	private EventInfo fetchEvent(String url) {
		IsvSpecificAppmarketCredentials credentials = credentialsSupplier.get();
		EventInfo event = appmarketEventFetcher.fetchEvent(url, credentials.getIsvKey(), credentials.getIsvSecret());
		log.info("Successfully retrieved event={}", event);
		return event;
	}

	private APIResult process(EventInfo event, String baseAppmarketUrl) {
		return eventProcessorRegistry.get(event.getType()).process(event, baseAppmarketUrl);
	}

	private String extractBaseAppmarketUrl(String eventUrl) {
		try {
			HttpHost httpHost = URIUtils.extractHost(new URI(eventUrl));
			return httpHost.toURI();
		} catch (Exception e) {
			log.error("Cannot parse event url", e);
			throw new IsvServiceException(format("Cannot parse event url=%s", eventUrl));
		}
	}
}
