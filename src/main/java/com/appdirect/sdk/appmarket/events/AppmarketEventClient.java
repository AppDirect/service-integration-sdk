package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.utils.EventIdExtractor.extractId;
import static java.lang.String.format;

import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.web.RestOperationsFactory;

/**
 * This class defines method for performing HTTP requests against an AppMarket instace
 */
@Slf4j
public class AppmarketEventClient {

	private final RestOperationsFactory restClientFactory;
	private final DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;

	AppmarketEventClient(RestOperationsFactory restClientFactory, DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier) {
		this.restClientFactory = restClientFactory;
		this.credentialsSupplier = credentialsSupplier;
	}

	/**
	 * Perform "signed fetch" in order to retrieve the payload of an event sent to the connector from the AppMarket
	 * @param url from which we can fetch the event payload
	 * @param key the client key to sign the fetch request with
	 * @param secret the client secret to sign the fetch request with
	 * @return an {@link EventInfo} instance representing the retrieved payload
	 */
	EventInfo fetchEvent(String url, String key, String secret) {
		log.debug("Consuming event from url={}", url);
		EventInfo fetchedEvent = restClientFactory
			.restOperationsForProfile(key, secret)
			.getForObject(url, EventInfo.class);
		fetchedEvent.setId(extractId(url));
		return fetchedEvent;
	}

	/**
	 * Send an "event resolved" notification for an asyncronous event. It serves to notify the
	 * AppMarket that the processing of a given event by the connector has been completed 
	 * @param baseAppmarketUrl host on which the marketplace is running
	 * @param eventId the id of the event we would like to resolve
	 * @param result represents the event processing result sent to the AppMarket. It would infcate if the event
	 *               processing has been successful or not.   
	 * @param key the client key used to sign the resolve request
	 */
	public void resolve(String baseAppmarketUrl, String eventId, APIResult result, String key) {
		String url = eventResolutionEndpoint(baseAppmarketUrl, eventId);
		String secret = credentialsSupplier.getConsumerCredentials(key).developerSecret;

		restClientFactory.restOperationsForProfile(key, secret).postForObject(url, result, String.class);
		log.info("Resolved event with eventId={} with apiResult={}", eventId, result);
	}

	private String eventResolutionEndpoint(String baseAppmarketUrl, String eventId) {
		return format("%s/api/integration/v1/events/%s/result", baseAppmarketUrl, eventId);
	}
}
