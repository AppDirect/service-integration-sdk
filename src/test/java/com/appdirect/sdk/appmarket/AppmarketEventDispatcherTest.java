package com.appdirect.sdk.appmarket;

import static com.appdirect.sdk.appmarket.api.EventType.SUBSCRIPTION_ORDER;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;

import org.junit.Test;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventInfo;

public class AppmarketEventDispatcherTest {
	private AppmarketEventDispatcher eventDispatcher;

	@Test
	public void testDispatchAndHandle_defaultsToUnknownEventProcessor() throws Exception {
		eventDispatcher = new AppmarketEventDispatcher(new HashMap<>());

		APIResult results = eventDispatcher.dispatchAndHandle(someSubscriptionOrderEvent());

		assertThat(results.getMessage()).isEqualTo("Unsupported event type SUBSCRIPTION_ORDER");
	}

	@Test
	public void testDispatchAndHandle_sendsEventTo() throws Exception {
		eventDispatcher = new AppmarketEventDispatcher(new HashMap<>());

		APIResult results = eventDispatcher.dispatchAndHandle(someSubscriptionOrderEvent());

		assertThat(results.getMessage()).isEqualTo("Unsupported event type SUBSCRIPTION_ORDER");
	}

	private EventInfo someSubscriptionOrderEvent() {
		return EventInfo.builder().type(SUBSCRIPTION_ORDER).build();
	}
}
