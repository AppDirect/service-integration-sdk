package com.appdirect.sdk.appmarket.events;

import java.util.Map;

class SubscriptionClosedParser implements EventParser<SubscriptionClosed> {
	@Override
	public SubscriptionClosed parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo, Map<String, String[]> queryParams) {
		return new SubscriptionClosed(eventInfo.getPayload().getAccount());
	}
}
