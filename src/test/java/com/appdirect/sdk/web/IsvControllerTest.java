package com.appdirect.sdk.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.appdirect.sdk.marketplace.api.vo.APIResult;

public class IsvControllerTest {

	private IsvEventService service;
	private IsvController controller;

	@BeforeMethod
	public void setup() throws Exception {
		service = mock(IsvEventService.class);

		controller = new IsvController(service);
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
