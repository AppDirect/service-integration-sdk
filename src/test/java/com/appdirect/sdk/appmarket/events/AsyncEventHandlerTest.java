package com.appdirect.sdk.appmarket.events;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.concurrent.Executor;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class AsyncEventHandlerTest {

	private Executor executor = mock(Executor.class);
	private AsyncEventHandler asyncEventHandler = new AsyncEventHandler(executor);

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

		APIResult result = asyncEventHandler.handle(someEventHandler, "some-key", eventToHandle);

		ArgumentCaptor<Runnable> eventHandlingCaptor = ArgumentCaptor.forClass(Runnable.class);
		verify(executor).execute(eventHandlingCaptor.capture());

		Runnable eventHandling = eventHandlingCaptor.getValue();
		eventHandling.run();

		verify(someEventHandler).handle("some-key", eventToHandle);
	}

	private EventInfo someEvent() {
		return EventInfo.builder().build();
	}
}
