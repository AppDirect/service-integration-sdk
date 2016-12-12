package com.appdirect.sdk.appmarket.events;

import java.util.Map;

class SubscriptionReactivatedParser implements EventParser<SubscriptionReactivated> {
	@Override
	public SubscriptionReactivated parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo, Map<String, String[]> queryParams) {
		return new SubscriptionReactivated(eventInfo.getPayload().getAccount());
	}
}
