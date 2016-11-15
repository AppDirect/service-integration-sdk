package com.appdirect.sdk.web;

import static com.appdirect.sdk.appmarket.api.ErrorCode.UNKNOWN_ERROR;
import static com.appdirect.sdk.appmarket.api.EventFlag.STATELESS;
import static com.appdirect.sdk.appmarket.api.EventType.ACCOUNT_UNSYNC;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.appdirect.sdk.appmarket.AppmarketEventDispatcher;
import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.exception.DeveloperServiceException;

@RunWith(MockitoJUnitRunner.class)
public class AppmarketEventServiceTest {

	@Mock
	private AppmarketEventFetcher appmarketEventFetcher;
	@Mock
	private AppmarketEventDispatcher eventDispatcher;
	@Mock
	private DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;

	private AppmarketEventService testedService;

	@Before
	public void setUp() throws Exception {
		testedService = new AppmarketEventService(appmarketEventFetcher, credentialsSupplier, eventDispatcher);
	}

	@Test
	public void processEvent_dispatcherIsCalled_andExpectedResultIsReturned() throws Exception {
		//Given
		EventInfo testEvent = EventInfo.builder()
				.type(ACCOUNT_UNSYNC)
				.build();
		APIResult expectedProcessingResult = new APIResult(true, "Event Processing Successful");

		when(credentialsSupplier.apply(anyString()))
				.thenReturn(someCredentials("testKey", "testSecret"));

		when(appmarketEventFetcher.fetchEvent("http://test.url.org", "testKey", "testSecret"))
				.thenReturn(testEvent);

		when(eventDispatcher.dispatchAndHandle(testEvent))
				.thenReturn(expectedProcessingResult);

		//When
		APIResult actualResponse = testedService.processEvent("http://test.url.org");

		//Then
		assertThat(actualResponse).isEqualTo(expectedProcessingResult);
	}

	@Test
	public void processEvent_whenBusinessLevelExceptionThrown_thenItBubblesUp() {
		//Given
		DeveloperServiceException expectedException = new DeveloperServiceException("Bad stuff happened");

		when(credentialsSupplier.apply(anyString()))
				.thenReturn(someCredentials("testKey", "testSecret"));

		when(appmarketEventFetcher.fetchEvent("http://test.url.org", "testKey", "testSecret"))
				.thenThrow(expectedException);

		//Then
		assertThatThrownBy(() -> testedService.processEvent("http://test.url.org"))
				.isEqualTo(expectedException);
	}

	@Test
	public void processEvent_whenUnknownExceptionThrown_thenBusinessLevelExceptionWithUnknownErrorCodeIsReturned() {
		//Given
		when(credentialsSupplier.apply(anyString()))
				.thenReturn(someCredentials("testKey", "testSecret"));

		when(appmarketEventFetcher.fetchEvent("http://test.url.org", "testKey", "testSecret"))
				.thenThrow(new RuntimeException());

		//When
		Throwable exceptionCaught = catchThrowable(() -> testedService.processEvent("http://test.url.org"));

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

		when(credentialsSupplier.apply(anyString()))
				.thenReturn(someCredentials("testKey", "testSecret"));

		when(appmarketEventFetcher.fetchEvent("http://test.url.org", "testKey", "testSecret"))
				.thenReturn(testEvent);

		//When
		APIResult actualResult = testedService.processEvent("http://test.url.org");

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

		when(credentialsSupplier.apply(anyString())).thenReturn(someCredentials("testKey", "testSecret"));

		//When
		Throwable exceptionCaught = catchThrowable(() -> testedService.processEvent(invalidUrl));

		//Then
		assertThat(exceptionCaught)
				.isExactlyInstanceOf(DeveloperServiceException.class)
				.hasFieldOrPropertyWithValue("result.message", expectedErrorMessage);
	}

	private Credentials someCredentials(String key, String secret) {
		return new Credentials(key, secret);
	}
}
