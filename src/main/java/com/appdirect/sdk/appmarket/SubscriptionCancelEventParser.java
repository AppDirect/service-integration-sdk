package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.SubscriptionCancel;

class SubscriptionCancelEventParser implements EventParser<SubscriptionCancel> {
	@Override
	public SubscriptionCancel parse(EventInfo event) {
		return new SubscriptionCancel(
			event.getPayload().getAccount().getAccountIdentifier()
		);
	}
}
