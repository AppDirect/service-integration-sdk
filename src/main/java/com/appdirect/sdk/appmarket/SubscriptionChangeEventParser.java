package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.SubscriptionChange;

class SubscriptionChangeEventParser implements EventParser<SubscriptionChange> {

	@Override
	public SubscriptionChange parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		return new SubscriptionChange(
				consumerKeyUsedByTheRequest,
				eventInfo.getCreator(),
				eventInfo.getPayload().getOrder(),
				eventInfo.getPayload().getAccount()
		);
	}
}
