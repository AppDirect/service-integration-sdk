package com.appdirect.sdk;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.appdirect.sdk.web.oauth.OAuthKeyExtractor;

public class AppmarketEventControllerTest {

	private AppmarketEventService service;
	private AppmarketEventController controller;
	private OAuthKeyExtractor keyExtractor;

	@Before
	public void setup() throws Exception {
		service = mock(AppmarketEventService.class);
		keyExtractor = mock(OAuthKeyExtractor.class);

		controller = new AppmarketEventController(service, keyExtractor);
	}

	@Test
	public void processEvent_sendsTheRequestToTheKeyExtractor_andSendsTheKeyToTheService() throws Exception {
		HttpServletRequest someRequest = mock(HttpServletRequest.class);
		when(keyExtractor.extractFrom(someRequest)).thenReturn("the-key-from-the-request");

		controller.processEvent(someRequest, "some-event-url");

		verify(service).processEvent("some-event-url", "the-key-from-the-request");
	}

	@Test
	public void processEvent_sendsTheEventToTheService_andReturnsItsResults() throws Exception {
		when(keyExtractor.extractFrom(any())).thenReturn("da-key");
		APIResult aHugeSuccess = aHugeSuccess();
		when(service.processEvent("some-event-url", "da-key")).thenReturn(aHugeSuccess);

		ResponseEntity<APIResult> response = controller.processEvent(anyRequest(), "some-event-url");

		assertThat(response.getBody()).isEqualTo(aHugeSuccess);
		assertThat(response.getStatusCode()).isEqualTo(OK);
	}

	private HttpServletRequest anyRequest() {
		return mock(HttpServletRequest.class);
	}

	private APIResult aHugeSuccess() {
		return new APIResult(true, "HUGE SUCCESS!");
	}
}
