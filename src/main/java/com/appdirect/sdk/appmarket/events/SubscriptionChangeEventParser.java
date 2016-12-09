package com.appdirect.sdk.appmarket.events;

import java.util.Map;

class SubscriptionChangeEventParser implements EventParser<SubscriptionChange> {

	@Override
	public SubscriptionChange parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo, Map<String, String[]> queryParams) {
		return new SubscriptionChange(
				consumerKeyUsedByTheRequest,
				eventInfo.getCreator(),
				eventInfo.getPayload().getOrder(),
				eventInfo.getPayload().getAccount()
		);
	}
}
