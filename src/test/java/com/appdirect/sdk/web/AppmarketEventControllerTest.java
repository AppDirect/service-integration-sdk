package com.appdirect.sdk.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.appdirect.sdk.appmarket.api.APIResult;

public class AppmarketEventControllerTest {

	private AppmarketEventService service;
	private AppmarketEventController controller;

	@Before
	public void setup() throws Exception {
		service = mock(AppmarketEventService.class);

		controller = new AppmarketEventController(service);
	}

	@Test
	public void processEvent_sendsTheEventToTheService_andReturnsItsResults() throws Exception {
		APIResult aHugeSuccess = aHugeSuccess();
		when(service.processEvent("some-event-url")).thenReturn(aHugeSuccess);

		ResponseEntity<APIResult> response = controller.processEvent("some-event-url");

		assertThat(response.getBody()).isEqualTo(aHugeSuccess);
		assertThat(response.getStatusCode()).isEqualTo(OK);
	}

	private APIResult aHugeSuccess() {
		return new APIResult(true, "HUGE SUCCESS!");
	}
}
