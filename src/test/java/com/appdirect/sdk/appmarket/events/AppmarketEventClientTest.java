package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.APIResult.success;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_CANCEL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.web.RestOperationsFactory;

public class AppmarketEventClientTest {
	private RestOperationsFactory restOperationsFactory;
	private RestTemplate restOperations;
	private DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;

	private AppmarketEventClient testedFetcher;

	@Before
	public void setUp() throws Exception {
		restOperationsFactory = mock(RestOperationsFactory.class);
		restOperations = mock(RestTemplate.class);
		credentialsSupplier = mock(DeveloperSpecificAppmarketCredentialsSupplier.class);

		testedFetcher = new AppmarketEventClient(restOperationsFactory, credentialsSupplier);

		when(credentialsSupplier.getConsumerCredentials("some-key")).thenReturn(new Credentials("some-key", "some-secret"));
	}

	@Test
	public void fetchEvent_callsGet_onTheRightUrl() throws Exception {
		//Given
		String testUrl = "testUrl";
		String testKey = "testKey";
		String testSecret = "testSecret";
		EventInfo testEventInfo = EventInfo.builder().build();

		when(restOperationsFactory.restOperationsForProfile(testKey, testSecret))
				.thenReturn(restOperations);
		when(restOperations.getForObject(testUrl, EventInfo.class))
				.thenReturn(testEventInfo);

		//When
		EventInfo fetchedEvent = testedFetcher.fetchEvent(testUrl, testKey, testSecret);

		//Then
		assertThat(fetchedEvent).isEqualTo(testEventInfo);
	}

	@Test
	public void resolveEvent_callsPost_onTheRightUrl() throws Exception {
		when(restOperationsFactory.restOperationsForProfile("some-key", "some-secret")).thenReturn(restOperations);
		EventInfo cancelEvent = someEvent(SUBSCRIPTION_CANCEL, "http://base.com");
		APIResult resultToSend = success("async is resolved");

		testedFetcher.resolve(cancelEvent, resultToSend, "some-key", "https://www.acme-marketplace.com/api/integration/v1/events/id-of-the-event");

		verify(restOperations).postForObject("http://base.com/api/integration/v1/events/id-of-the-event/result", resultToSend, String.class);
	}

	private EventInfo someEvent(EventType type, String baseUrl) {
		MarketInfo marketInfo = new MarketInfo("some-partner", baseUrl);
		return EventInfo.builder().type(type).marketplace(marketInfo).build();
	}
}
