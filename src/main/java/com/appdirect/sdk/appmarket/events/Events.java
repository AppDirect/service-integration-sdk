package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_NOTICE;
import static java.util.Optional.ofNullable;

class Events {
	boolean eventShouldBeHandledAsync(EventInfo rawEvent) {
		return rawEvent.getType() != SUBSCRIPTION_NOTICE;
	}

	String extractEditionCode(EventInfo rawEvent) {
		return ofNullable(rawEvent.getPayload())
				.map(EventPayload::getOrder)
				.map(OrderInfo::getEditionCode)
				.orElse(null);
	}
}
