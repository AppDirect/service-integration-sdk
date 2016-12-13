package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.ErrorCode.UNKNOWN_ERROR;
import static com.appdirect.sdk.appmarket.events.EventFlag.STATELESS;
import static com.appdirect.sdk.appmarket.events.EventType.ACCOUNT_UNSYNC;
import static com.appdirect.sdk.support.QueryParameters.oneQueryParam;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Map;

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

		when(credentialsSupplier.getConsumerCredentials("testKey")).thenReturn(someCredentials("testKey", "testSecret"));
	}

	@Test
	public void processEvent_dispatcherIsCalled_andExpectedResultIsReturned() throws Exception {
		//Given
		EventInfo testEvent = EventInfo.builder()
				.type(ACCOUNT_UNSYNC)
				.build();
		APIResult expectedProcessingResult = new APIResult(true, "Event Processing Successful");

		when(appmarketEventClient.fetchEvent("http://test.url.org", "testKey", "testSecret"))
				.thenReturn(testEvent);

		Map<String, String[]> queryParams = oneQueryParam();
		when(eventDispatcher.dispatchAndHandle(testEvent, eventContext("testKey", queryParams)))
				.thenReturn(expectedProcessingResult);

		//When
		APIResult actualResponse = testedService.processEvent("http://test.url.org", eventContext("testKey", queryParams));

		//Then
		assertThat(actualResponse).isEqualTo(expectedProcessingResult);
	}

	@Test
	public void processEvent_whenBusinessLevelExceptionThrown_thenItBubblesUp() {
		//Given
		DeveloperServiceException expectedException = new DeveloperServiceException("Bad stuff happened");

		when(appmarketEventClient.fetchEvent("http://test.url.org", "testKey", "testSecret"))
				.thenThrow(expectedException);

		//Then
		assertThatThrownBy(() -> testedService.processEvent("http://test.url.org", eventContext("testKey", oneQueryParam())))
				.isEqualTo(expectedException);
	}

	@Test
	public void processEvent_whenUnknownExceptionThrown_thenBusinessLevelExceptionWithUnknownErrorCodeIsReturned() {
		//Given
		when(appmarketEventClient.fetchEvent("http://test.url.org", "testKey", "testSecret"))
				.thenThrow(new RuntimeException());

		//When
		Throwable exceptionCaught = catchThrowable(() -> testedService.processEvent("http://test.url.org", eventContext("testKey", oneQueryParam())));

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

		when(appmarketEventClient.fetchEvent("http://test.url.org", "testKey", "testSecret"))
				.thenReturn(testEvent);

		//When
		APIResult actualResult = testedService.processEvent("http://test.url.org", eventContext("testKey", oneQueryParam()));

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
		String expectedErrorMessage = format("Failed to process event. eventUrl=%s", invalidUrl);

		//When
		Throwable exceptionCaught = catchThrowable(() -> testedService.processEvent(invalidUrl, eventContext("testKey", oneQueryParam())));

		//Then
		assertThat(exceptionCaught)
				.isExactlyInstanceOf(DeveloperServiceException.class)
				.hasFieldOrPropertyWithValue("result.message", expectedErrorMessage);
	}

	private Credentials someCredentials(String key, String secret) {
		return new Credentials(key, secret);
	}

	private EventExecutionContext eventContext(String consumerKey, Map<String, String[]> queryParams) {
		return new EventExecutionContext(consumerKey, queryParams);
	}
}
