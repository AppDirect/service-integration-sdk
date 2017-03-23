package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.APIResult.success;
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
	public void fetchEvent_callsGet_onTheRightUrl_andSetsEventId() throws Exception {
		//Given
		String testUrl = "some.com/events/the-id-inferred-from-url";
		String testKey = "testKey";
		String testSecret = "testSecret";
		EventInfo testEventInfo = EventInfo.builder().build();

		when(restOperationsFactory.restOperationsForProfile(testKey, testSecret))
				.thenReturn(restOperations);
		when(restOperations.getForObject(testUrl, EventInfo.class))
				.thenReturn(testEventInfo);
		String testDeveloperKey = "testKey";
		String testDeveloperSecret = "testSecret";
		Credentials testCredenials = new Credentials(testDeveloperKey, testDeveloperSecret);
		//When
		EventInfo fetchedEvent = testedFetcher.fetchEvent(testUrl, testCredenials);

		//Then
		assertThat(fetchedEvent).isEqualTo(testEventInfo);
		assertThat(fetchedEvent.getId()).isEqualTo("the-id-inferred-from-url");
	}

	@Test
	public void resolveEvent_callsPost_onTheRightUrl() throws Exception {
		when(restOperationsFactory.restOperationsForProfile("some-key", "some-secret")).thenReturn(restOperations);
		APIResult resultToSend = success("async is resolved");

		testedFetcher.resolve("http://base.com", "id-of-the-event", resultToSend, "some-key");

		verify(restOperations).postForObject("http://base.com/api/integration/v1/events/id-of-the-event/result", resultToSend, String.class);
	}
}
