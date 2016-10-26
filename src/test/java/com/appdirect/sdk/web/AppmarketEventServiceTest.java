package com.appdirect.sdk.web;

import static com.appdirect.sdk.appmarket.api.vo.ErrorCode.UNKNOWN_ERROR;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.appdirect.sdk.appmarket.AppmarketEventProcessor;
import com.appdirect.sdk.appmarket.AppmarketEventProcessorRegistry;
import com.appdirect.sdk.appmarket.IsvSpecificAppmarketCredentials;
import com.appdirect.sdk.appmarket.IsvSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.api.type.EventType;
import com.appdirect.sdk.appmarket.api.vo.APIResult;
import com.appdirect.sdk.appmarket.api.vo.ErrorCode;
import com.appdirect.sdk.appmarket.api.vo.EventFlag;
import com.appdirect.sdk.appmarket.api.vo.EventInfo;
import com.appdirect.sdk.exception.IsvServiceException;

@RunWith(MockitoJUnitRunner.class)
public class AppmarketEventServiceTest {

	@Mock
	private AppmarketEventFetcher appmarketEventFetcher;
	@Mock
	private AppmarketEventProcessorRegistry eventProcessorRegistry;
	@Mock
	private IsvSpecificAppmarketCredentialsSupplier credentialsSupplier;

	private AppmarketEventService testedService;

	@Before
	public void setUp() throws Exception {
		testedService = new AppmarketEventService(appmarketEventFetcher, eventProcessorRegistry, credentialsSupplier);
	}

	@Test
	public void processEvent_whenNoExceptionsAreThrown_thenExpectedResultIsReturned() throws Exception {
		//Given
		String testUrl = "http://test.url.org";
		String testKey = "testKey";
		String testSecret = "testSecret";
		IsvSpecificAppmarketCredentials testCredentials = new IsvSpecificAppmarketCredentials(testKey, testSecret);
		EventType testEventType = EventType.ACCOUNT_UNSYNC;
		EventInfo testEvent = EventInfo.builder()
			.type(testEventType)
			.build();
		AppmarketEventProcessor mockProcessor = mock(AppmarketEventProcessor.class);
		APIResult expectedProcessingResult = new APIResult(true, "Event Processing Successful");

		when(credentialsSupplier.get())
			.thenReturn(testCredentials);

		when(appmarketEventFetcher.fetchEvent(testUrl, testKey, testSecret))
			.thenReturn(testEvent);

		when(eventProcessorRegistry.get(testEventType))
			.thenReturn(mockProcessor);

		when(mockProcessor.process(testEvent, testUrl))
			.thenReturn(expectedProcessingResult);

		//When
		APIResult actualResponse = testedService.processEvent(testUrl);

		//Then
		assertThat(actualResponse).isEqualTo(expectedProcessingResult);
	}

	@Test
	public void processEvent_whenBusinessLevelExceptionThrown_thenItBubblesUp() {
		//Given
		String testUrl = "http://test.url.org";
		String testKey = "testKey";
		String testSecret = "testSecret";
		IsvSpecificAppmarketCredentials testCredentials = new IsvSpecificAppmarketCredentials(testKey, testSecret);
		IsvServiceException expectedException = new IsvServiceException("Bad stuff happened");

		when(credentialsSupplier.get())
			.thenReturn(testCredentials);

		when(appmarketEventFetcher.fetchEvent(testUrl, testKey, testSecret))
			.thenThrow(expectedException);

		//Then
		assertThatThrownBy(() -> testedService.processEvent(testUrl))
			.isEqualTo(expectedException);
	}

	@Test
	public void processEvent_whenUnknownExceptionThrown_thenBusinessLevelExceptionWithUnknownErrorCodeIsReturned() {
		//Given
		String testUrl = "http://test.url.org";
		String testKey = "testKey";
		String testSecret = "testSecret";
		IsvSpecificAppmarketCredentials testCredentials = new IsvSpecificAppmarketCredentials(testKey, testSecret);

		when(credentialsSupplier.get())
			.thenReturn(testCredentials);

		when(appmarketEventFetcher.fetchEvent(testUrl, testKey, testSecret))
			.thenThrow(new RuntimeException());

		//When
		Throwable exceptionCaught = catchThrowable(() -> testedService.processEvent(testUrl));

		//Then
		assertThat(exceptionCaught).isExactlyInstanceOf(IsvServiceException.class);
		ErrorCode actualExceptionErrorCode = ((IsvServiceException) exceptionCaught).getResult().getErrorCode();
		assertThat(actualExceptionErrorCode).isEqualTo(UNKNOWN_ERROR);
	}

	@Test
	public void testProcessEvent_ifFetchedEventIsStateless_thenReturnSuccess() {
		//Given
		String testUrl = "http://test.url.org";
		String testKey = "testKey";
		String testSecret = "testSecret";
		IsvSpecificAppmarketCredentials testCredentials = new IsvSpecificAppmarketCredentials(testKey, testSecret);
		EventInfo testEvent = EventInfo.builder()
			.flag(EventFlag.STATELESS)
			.build();

		when(credentialsSupplier.get())
			.thenReturn(testCredentials);

		when(appmarketEventFetcher.fetchEvent(testUrl, testKey, testSecret))
			.thenReturn(testEvent);

		//When
		APIResult actualResult = testedService.processEvent(testUrl);

		//Then
		assertThat(actualResult.isSuccess())
			.as("The returned result is a success")
			.isTrue();
	}

	@Test
	public void testProcessEvent_ifTheEventUrlIsInvalid_thenABusinessLevelExceptionWithAppropriateMessageIsThrown() {
		//Given
		String invalidUrl = "inVaLidUrl";
		String expectedErrorMessage = format("Cannot parse event url=%s", invalidUrl);

		//When
		Throwable exceptionCaught = catchThrowable(() -> testedService.processEvent(invalidUrl));

		//Then
		assertThat(exceptionCaught).isExactlyInstanceOf(IsvServiceException.class);
		String actualExceptionErrorMessage = ((IsvServiceException) exceptionCaught).getResult().getMessage();
		assertThat(actualExceptionErrorMessage).isEqualTo(expectedErrorMessage);
	}
}
