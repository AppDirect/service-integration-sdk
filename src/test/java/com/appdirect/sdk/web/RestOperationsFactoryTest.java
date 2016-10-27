package com.appdirect.sdk.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.appdirect.sdk.web.exception.AppmarketEventConsumerExceptionHandler;

@RunWith(MockitoJUnitRunner.class)
public class RestOperationsFactoryTest {
	
	@Mock
	private AppmarketEventConsumerExceptionHandler appmarketEventConsumerExceptionHandler;

	private RestOperationsFactory testRestOperationsFactory;

	@Before
	public void setUp() throws Exception {
		testRestOperationsFactory = new RestOperationsFactory(appmarketEventConsumerExceptionHandler);
	}

	@Test
	public void restOperationsForProfile() throws Exception {
		//Given
		String testKey = "testKey";
		String testSecret = "testSecret";

		//When
		RestTemplate restClient = testRestOperationsFactory.restOperationsForProfile(testKey, testSecret);

		//Then
		assertThat(restClient.getErrorHandler()).isEqualTo(appmarketEventConsumerExceptionHandler);
	}

}
