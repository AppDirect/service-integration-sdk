package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.SubscriptionReactivated;

class SubscriptionReactivatedParser implements EventParser<SubscriptionReactivated> {
	@Override
	public SubscriptionReactivated parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		return new SubscriptionReactivated(eventInfo.getPayload().getAccount());
	}
}
