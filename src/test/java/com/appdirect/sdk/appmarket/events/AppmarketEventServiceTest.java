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

import static com.appdirect.sdk.appmarket.events.ErrorCode.UNKNOWN_ERROR;
import static com.appdirect.sdk.appmarket.events.EventFlag.STATELESS;
import static com.appdirect.sdk.appmarket.events.EventHandlingContexts.eventContext;
import static com.appdirect.sdk.appmarket.events.EventType.ACCOUNT_UNSYNC;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.exception.DeveloperServiceException;

@RunWith(MockitoJUnitRunner.class)
public class AppmarketEventServiceTest {

	@Mock
	private AppmarketEventClient appmarketEventClient;
	@Mock
	private AppmarketEventDispatcher eventDispatcher;
	@Mock
	private DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;

	private AppmarketEventService testedService;

	@Before
	public void setUp() throws Exception {
		testedService = new AppmarketEventService(appmarketEventClient, credentialsSupplier, eventDispatcher);

		when(credentialsSupplier.getConsumerCredentials("testKey"))
				.thenReturn(new Credentials("testKey", "testSecret"));
	}

	@Test
	public void processEvent_dispatcherIsCalled_andExpectedResultIsReturned() throws Exception {
		//Given
		EventInfo testEvent = EventInfo.builder()
				.type(ACCOUNT_UNSYNC)
				.build();
		APIResult expectedProcessingResult = new APIResult(true, "Event Processing Successful");
		String testDeveloperKey = "testKey";
		String testDeveloperSecret = "testSecret";
		Credentials testCredentials = new Credentials(testDeveloperKey, testDeveloperSecret);
		when(appmarketEventClient.fetchEvent("http://test.url.org", testCredentials))
				.thenReturn(testEvent);

		EventHandlingContext eventContext = eventContext("testKey");
		when(eventDispatcher.dispatchAndHandle(testEvent, eventContext))
				.thenReturn(expectedProcessingResult);

		//When
		APIResult actualResponse = testedService.processEvent("http://test.url.org", eventContext);

		//Then
		assertThat(actualResponse).isEqualTo(expectedProcessingResult);
	}

	@Test
	public void processEvent_whenBusinessLevelExceptionThrown_thenItBubblesUp() {
		//Given
		DeveloperServiceException expectedException = new DeveloperServiceException("Bad stuff happened");

		String testDeveloperKey = "testKey";
		String testDeveloperSecret = "testSecret";
		Credentials testCredentials = new Credentials(testDeveloperKey, testDeveloperSecret);
		when(appmarketEventClient.fetchEvent("http://test.url.org", testCredentials))
				.thenThrow(expectedException);

		//Then
		assertThatThrownBy(() -> testedService.processEvent("http://test.url.org", eventContext("testKey")))
				.isEqualTo(expectedException);
	}

	@Test
	public void processEvent_whenUnknownExceptionThrown_thenBusinessLevelExceptionWithUnknownErrorCodeIsReturned() {
		//Given
		String testDeveloperKey = "testKey";
		String testDeveloperSecret = "testSecret";
		Credentials testCredentials = new Credentials(testDeveloperKey, testDeveloperSecret);
		when(appmarketEventClient.fetchEvent("http://test.url.org", testCredentials))
				.thenThrow(new RuntimeException());

		//When
		Throwable exceptionCaught = catchThrowable(() -> testedService.processEvent("http://test.url.org", eventContext("testKey")));

		//Then
		assertThat(exceptionCaught)
				.isExactlyInstanceOf(DeveloperServiceException.class)
				.hasFieldOrPropertyWithValue("result.errorCode", UNKNOWN_ERROR);
	}

	@Test
	public void testProcessEvent_ifFetchedEventIsStateless_thenReturnSuccessAndDoNotDispatchIt() {
		//Given
		EventInfo testEvent = EventInfo.builder()
				.flag(STATELESS)
				.build();
		String testDeveloperKey = "testKey";
		String testDeveloperSecret = "testSecret";
		Credentials testCredentials = new Credentials(testDeveloperKey, testDeveloperSecret);
		when(appmarketEventClient.fetchEvent("http://test.url.org", testCredentials))
				.thenReturn(testEvent);

		//When
		APIResult actualResult = testedService.processEvent("http://test.url.org", eventContext("testKey"));

		//Then
		assertThat(actualResult.isSuccess())
				.as("The returned result is a success")
				.isTrue();
		verifyZeroInteractions(eventDispatcher);
	}

	@Test
	public void testProcessEvent_ifTheEventUrlIsInvalid_thenABusinessLevelExceptionWithAppropriateMessageIsThrown() {
		//Given
		String invalidUrl = "inVaLidUrl";
		String expectedErrorMessage = format("Failed to process event. eventUrl=%s | exception=Url is not valid.", invalidUrl);
		when(appmarketEventClient.fetchEvent(any(), any())).thenThrow(new IllegalArgumentException("Url is not valid."));

		//When
		Throwable exceptionCaught = catchThrowable(() -> testedService.processEvent(invalidUrl, eventContext("testKey")));

		//Then
		assertThat(exceptionCaught)
				.isExactlyInstanceOf(DeveloperServiceException.class)
				.hasFieldOrPropertyWithValue("result.message", expectedErrorMessage);
	}
}
