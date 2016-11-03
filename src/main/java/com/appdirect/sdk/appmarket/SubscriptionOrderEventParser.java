package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.SubscriptionOrder;

/**
 * To see what is mandatory or not, consult https://docs.appdirect.com/developer/distribution/event-notifications/subscription-events#attributes
 */
class SubscriptionOrderEventParser implements EventParser<SubscriptionOrder> {
	@Override
	public SubscriptionOrder parse(EventInfo event) {
		return new SubscriptionOrder(
				event.getFlag(),
				event.getCreator(),
				event.getPayload().getConfiguration(),
				event.getPayload().getCompany(),
				event.getPayload().getOrder());
	}
}
