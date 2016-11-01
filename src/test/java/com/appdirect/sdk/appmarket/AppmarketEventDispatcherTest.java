package com.appdirect.sdk.appmarket;

import static com.appdirect.sdk.appmarket.api.APIResult.failure;
import static com.appdirect.sdk.appmarket.api.APIResult.success;
import static com.appdirect.sdk.appmarket.api.ErrorCode.CONFIGURATION_ERROR;
import static com.appdirect.sdk.appmarket.api.ErrorCode.INVALID_RESPONSE;
import static com.appdirect.sdk.appmarket.api.EventType.SUBSCRIPTION_CANCEL;
import static com.appdirect.sdk.appmarket.api.EventType.SUBSCRIPTION_ORDER;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;

import org.junit.Test;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.EventType;

public class AppmarketEventDispatcherTest {
	private AppmarketEventDispatcher eventDispatcher;

	@Test
	public void testDispatchAndHandle_defaultsToUnknownEventProcessor() throws Exception {
		eventDispatcher = new AppmarketEventDispatcher(new HashMap<>());

		APIResult results = eventDispatcher.dispatchAndHandle(someSubOrderEvent());

		assertThat(results.getMessage()).isEqualTo("Unsupported event type SUBSCRIPTION_ORDER");
		assertThat(results.getErrorCode()).isEqualTo(CONFIGURATION_ERROR);
	}

	@Test
	public void testDispatchAndHandle_sendsEventToProperHandler() throws Exception {
		HashMap<EventType, SDKEventHandler<?>> handlers = new HashMap<>();
		handlers.put(SUBSCRIPTION_ORDER, event -> failure(INVALID_RESPONSE, "OH NO! I FAILED!"));
		handlers.put(SUBSCRIPTION_CANCEL, event -> success("AH AH! I SUCCEEDED!"));

		eventDispatcher = new AppmarketEventDispatcher(handlers);

		APIResult results = eventDispatcher.dispatchAndHandle(someSubCancelEvent());

		assertThat(results.getMessage()).isEqualTo("AH AH! I SUCCEEDED!");
	}

	private EventInfo someSubOrderEvent() {
		return EventInfo.builder().type(SUBSCRIPTION_ORDER).build();
	}

	private EventInfo someSubCancelEvent() {
		return EventInfo.builder().type(SUBSCRIPTION_CANCEL).build();
	}
}
