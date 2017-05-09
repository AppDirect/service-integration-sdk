package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.utils.EventIdExtractor.extractId;
import static java.lang.String.format;

import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.web.RestOperationsFactory;

/**
 * This class defines method for performing HTTP requests against an AppMarket instance
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
	 *
	 * @param url         from which we can fetch the event payload
	 * @param credentials the credentials used to sign the request
	 * @return an {@link EventInfo} instance representing the retrieved payload
	 */
	EventInfo fetchEvent(String url, Credentials credentials) {
		log.debug("Consuming event from url={}", url);
		EventInfo fetchedEvent = restClientFactory
				.restOperationsForProfile(credentials.developerKey, credentials.developerSecret)
				.getForObject(url, EventInfo.class);
		fetchedEvent.setId(extractId(url));
		return fetchedEvent;
	}

	/**
	 * Send an "event resolved" notification for an asynchronous event. It serves to notify the
	 * AppMarket that the processing of a given event by the connector has been completed
	 *
	 * @param baseAppmarketUrl host on which the marketplace is running
	 * @param eventToken          the id of the event we would like to resolve
	 * @param result           represents the event processing result sent to the AppMarket. It would indicate if the event
	 *                         processing has been successful or not.
	 * @param key              the client key used to sign the resolve request
	 */
	public void resolve(String baseAppmarketUrl, String eventToken, APIResult result, String key) {
		String url = eventResolutionEndpoint(baseAppmarketUrl, eventToken);
		String secret = credentialsSupplier.getConsumerCredentials(key).developerSecret;

		restClientFactory.restOperationsForProfile(key, secret).postForObject(url, result, String.class);
		log.info("Resolved event with eventToken={} with apiResult={}", eventToken, result);
	}

	private String eventResolutionEndpoint(String baseAppmarketUrl, String eventToken) {
		return format("%s/api/integration/v1/events/%s/result", baseAppmarketUrl, eventToken);
	}
}
