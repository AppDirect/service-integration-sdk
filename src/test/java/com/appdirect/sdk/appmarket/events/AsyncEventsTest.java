package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.EventType.ADDON_ORDER;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_CANCEL;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_CHANGE;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_NOTICE;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_ORDER;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class AsyncEventsTest {

	@Test
	public void allEventsShouldBeHandledAsync_butSubscriptionNotice() throws Exception {
		AsyncEvents asyncEvents = new AsyncEvents();

		assertThat(asyncEvents.eventShouldBeHandledAsync(eventOfType(SUBSCRIPTION_ORDER))).isTrue();
		assertThat(asyncEvents.eventShouldBeHandledAsync(eventOfType(SUBSCRIPTION_CANCEL))).isTrue();
		assertThat(asyncEvents.eventShouldBeHandledAsync(eventOfType(SUBSCRIPTION_CHANGE))).isTrue();
		assertThat(asyncEvents.eventShouldBeHandledAsync(eventOfType(ADDON_ORDER))).isTrue();

		assertThat(asyncEvents.eventShouldBeHandledAsync(eventOfType(SUBSCRIPTION_NOTICE))).isFalse();
	}

	private EventInfo eventOfType(EventType eventType) {
		return EventInfo.builder().type(eventType).build();
	}
}
