package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.SubscriptionDeactivated;

class SubscriptionDeactivatedParser implements EventParser<SubscriptionDeactivated> {
	@Override
	public SubscriptionDeactivated parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		return new SubscriptionDeactivated(eventInfo.getPayload().getAccount());
	}
}
