package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.SubscriptionCancel;

class SubscriptionCancelEventParser implements EventParser<SubscriptionCancel> {
	@Override
	public SubscriptionCancel parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		return new SubscriptionCancel(
				consumerKeyUsedByTheRequest,
				eventInfo.getPayload().getAccount().getAccountIdentifier()
		);
	}
}
