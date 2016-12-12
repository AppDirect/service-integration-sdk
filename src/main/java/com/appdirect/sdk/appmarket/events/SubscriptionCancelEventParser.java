package com.appdirect.sdk.appmarket.events;

import java.util.Map;

class SubscriptionCancelEventParser implements EventParser<SubscriptionCancel> {
	@Override
	public SubscriptionCancel parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo, Map<String, String[]> queryParams) {
		return new SubscriptionCancel(
				consumerKeyUsedByTheRequest,
				eventInfo.getPayload().getAccount().getAccountIdentifier(),
				queryParams
		);
	}
}
