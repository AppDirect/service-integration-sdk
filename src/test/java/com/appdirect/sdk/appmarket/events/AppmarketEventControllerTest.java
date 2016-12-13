package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.APIResult.success;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import java.util.HashMap;
import java.util.Map;

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

		when(service.processEvent(anyString(), any())).thenReturn(aHugeSuccess());
	}

	@Test
	public void processEvent_sendsTheRequestToTheKeyExtractor_andSendsTheRightContextToTheService() throws Exception {
		Map<String, String[]> someParams = new HashMap<>();
		HttpServletRequest someRequest = mock(HttpServletRequest.class);
		when(someRequest.getParameterMap()).thenReturn(someParams);
		when(keyExtractor.extractFrom(someRequest)).thenReturn("the-key-from-the-request");

		controller.processEvent(someRequest, "some-event-url");

		verify(service).processEvent("some-event-url", new EventExecutionContext("the-key-from-the-request", someParams));
	}

	@Test
	public void processEvent_sendsTheEventToTheService_andReturnsItsResults() throws Exception {
		when(keyExtractor.extractFrom(any())).thenReturn("da-key");
		APIResult aHugeSuccess = aHugeSuccess();
		when(service.processEvent(eq("some-event-url"), any())).thenReturn(aHugeSuccess);

		ResponseEntity<APIResult> response = controller.processEvent(anyRequest(), "some-event-url");

		assertThat(response.getBody()).isEqualTo(aHugeSuccess);
		assertThat(response.getStatusCode()).isEqualTo(OK);
	}

	private HttpServletRequest anyRequest() {
		return mock(HttpServletRequest.class);
	}

	private APIResult aHugeSuccess() {
		return success("HUGE SUCCESS!");
	}
}
