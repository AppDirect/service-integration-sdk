package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.SubscriptionClosed;

class SubscriptionClosedParser implements EventParser<SubscriptionClosed> {
	@Override
	public SubscriptionClosed parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		return new SubscriptionClosed(eventInfo.getPayload().getAccount());
	}
}
