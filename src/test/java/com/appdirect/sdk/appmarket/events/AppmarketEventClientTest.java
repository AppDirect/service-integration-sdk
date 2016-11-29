package com.appdirect.sdk.appmarket.events;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import com.appdirect.sdk.web.RestOperationsFactory;

@RunWith(MockitoJUnitRunner.class)
public class AppmarketEventClientTest {
	@Mock
	private RestOperationsFactory restOperationsFactory;

	@Mock
	private RestTemplate restOperations;

	private AppmarketEventClient testedFetcher;

	@Before
	public void setUp() throws Exception {
		testedFetcher = new AppmarketEventClient(restOperationsFactory);
	}

	@Test
	public void fetchEvent() throws Exception {
		//Given
		String testUrl = "testUrl";
		String testKey = "testKey";
		String testSecret= "testSecret";
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

}
