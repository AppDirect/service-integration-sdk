/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.APIResult.success;
import static com.appdirect.sdk.appmarket.events.EventHandlingContexts.eventContext;
import static com.appdirect.sdk.support.QueryParameters.oneQueryParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

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
		Map<String, String[]> someParams = oneQueryParam("some", "params");
		HttpServletRequest someRequest = mock(HttpServletRequest.class);
		when(someRequest.getParameterMap()).thenReturn(someParams);
		when(keyExtractor.extractFrom(someRequest)).thenReturn("the-key-from-the-request");

		controller.processEvent(someRequest, "some-event-url");

		verify(service).processEvent("some-event-url", eventContext("the-key-from-the-request", someParams));
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

	private HttpServletRequest aRequestContainingHeader(String key, String value) {
		HttpServletRequest request = anyRequest();

		when(request.getHeader(key)).thenReturn(value);

		return request;
	}

	private HttpServletRequest anyRequest() {
		return mock(HttpServletRequest.class);
	}

	private APIResult aHugeSuccess() {
		return success("HUGE SUCCESS!");
	}
}
