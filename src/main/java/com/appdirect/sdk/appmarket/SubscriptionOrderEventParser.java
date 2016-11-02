package com.appdirect.sdk.appmarket;

import java.util.Optional;

import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.EventPayload;
import com.appdirect.sdk.appmarket.api.SubscriptionOrder;

/**
 * To see what is mandatory or not, consult https://docs.appdirect.com/developer/distribution/event-notifications/subscription-events#attributes
 */
class SubscriptionOrderEventParser implements EventParser<SubscriptionOrder> {
	@Override
	public SubscriptionOrder parse(EventInfo event) {
		return new SubscriptionOrder(
				event.getPayload().getConfiguration(),
				event.getFlag(),
				event.getCreator(),
				event.getPayload().getCompany());
	}

	private Optional<EventPayload> payload(EventInfo event) {
		return Optional.ofNullable(event.getPayload());
	}
}
