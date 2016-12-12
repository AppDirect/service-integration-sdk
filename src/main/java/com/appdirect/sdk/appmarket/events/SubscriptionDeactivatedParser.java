package com.appdirect.sdk.appmarket.events;

import java.util.Map;

class SubscriptionDeactivatedParser implements EventParser<SubscriptionDeactivated> {
	@Override
	public SubscriptionDeactivated parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo, Map<String, String[]> queryParams) {
		return new SubscriptionDeactivated(consumerKeyUsedByTheRequest, eventInfo.getPayload().getAccount(), queryParams);
	}
}
