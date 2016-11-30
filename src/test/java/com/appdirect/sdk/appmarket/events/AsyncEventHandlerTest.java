package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.APIResult.failure;
import static com.appdirect.sdk.appmarket.events.APIResult.success;
import static com.appdirect.sdk.appmarket.events.ErrorCode.ACCOUNT_NOT_FOUND;
import static com.appdirect.sdk.appmarket.events.ErrorCode.UNKNOWN_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.concurrent.Executor;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.appdirect.sdk.exception.DeveloperServiceException;

public class AsyncEventHandlerTest {

	private Executor executor = mock(Executor.class);
	private AppmarketEventClient appmarketEventClient = mock(AppmarketEventClient.class);
	private AsyncEventHandler asyncEventHandler = new AsyncEventHandler(executor, appmarketEventClient);

	@Test
	public void returnsOk() throws Exception {
		SDKEventHandler someEventHandler = (someKey, someEvent) -> null;

		APIResult result = asyncEventHandler.handle(someEventHandler, "some-key", someEvent());

		assertThat(result.isSuccess()).isTrue();
		assertThat(result.getStatusCodeReturnedToAppmarket()).isEqualTo(202);
		assertThat(result.getMessage()).isEqualTo("Event has been accepted by the connector. It will be processed soon.");
	}

	@Test
	public void handlesTheEventInTheExecutor() throws Exception {
		SDKEventHandler someEventHandler = mock(SDKEventHandler.class);
		EventInfo eventToHandle = someEvent();

		asyncEventHandler.handle(someEventHandler, "some-key", eventToHandle);

		Runnable eventHandling = extractRunnableFromExecutor();
		eventHandling.run();

		verify(someEventHandler).handle("some-key", eventToHandle);
	}

	@Test
	public void resolvesTheEventOnTheAppmarket() throws Exception {
		EventInfo eventToResolve = someEvent();
		APIResult result = success("After some async processing, I have now completed successfully");
		SDKEventHandler someEventHandler = mock(SDKEventHandler.class);
		when(someEventHandler.handle(anyString(), any())).thenReturn(result);

		asyncEventHandler.handle(someEventHandler, "some-key", eventToResolve);

		Runnable eventHandling = extractRunnableFromExecutor();
		eventHandling.run();

		verify(appmarketEventClient).resolve(eventToResolve, result, "some-key");
	}

	@Test
	public void doesNotResolveTheEventOnTheAppmarket_whenHandlerReturnsNull() throws Exception {
		SDKEventHandler someEventHandler = mock(SDKEventHandler.class);
		when(someEventHandler.handle(anyString(), any())).thenReturn(null);

		asyncEventHandler.handle(someEventHandler, "some-key", someEvent());

		Runnable eventHandling = extractRunnableFromExecutor();
		eventHandling.run();

		verifyZeroInteractions(appmarketEventClient);
	}

	@Test
	public void whenHandlerThrowsDeveloperServiceException_itsResultsAreSentToTheAppmarket() throws Exception {
		SDKEventHandler someEventHandler = mock(SDKEventHandler.class);
		when(someEventHandler.handle(anyString(), any())).thenThrow(new DeveloperServiceException(ACCOUNT_NOT_FOUND, "no account!"));

		asyncEventHandler.handle(someEventHandler, "some-key", someEvent());

		Runnable eventHandling = extractRunnableFromExecutor();
		eventHandling.run();

		verify(appmarketEventClient).resolve(any(), eq(failure(ACCOUNT_NOT_FOUND, "no account!")), anyString());
	}

	@Test
	public void whenHandlerThrowsAnyException_unknownErrorIsSentToTheAppmarket() throws Exception {
		SDKEventHandler someEventHandler = mock(SDKEventHandler.class);
		when(someEventHandler.handle(anyString(), any())).thenThrow(new IllegalArgumentException("some argument error"));

		asyncEventHandler.handle(someEventHandler, "some-key", someEvent());

		Runnable eventHandling = extractRunnableFromExecutor();
		eventHandling.run();

		verify(appmarketEventClient).resolve(any(), eq(failure(UNKNOWN_ERROR, "some argument error")), anyString());
	}

	private Runnable extractRunnableFromExecutor() {
		ArgumentCaptor<Runnable> eventHandlingCaptor = ArgumentCaptor.forClass(Runnable.class);
		verify(executor).execute(eventHandlingCaptor.capture());
		return eventHandlingCaptor.getValue();
	}

	private EventInfo someEvent() {
		return EventInfo.builder().build();
	}
}
