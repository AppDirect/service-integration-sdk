package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_NOTICE;

class Events {
	boolean eventShouldBeHandledAsync(EventInfo eventInfo) {
		return eventInfo.getType() != SUBSCRIPTION_NOTICE;
	}
}
