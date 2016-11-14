package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.SubscriptionChange;

class SubscriptionChangeEventParser implements EventParser<SubscriptionChange> {

	@Override
	public SubscriptionChange parse(EventInfo eventInfo) {
		return new SubscriptionChange(
			eventInfo.getCreator(),
			eventInfo.getPayload().getOrder(),
			eventInfo.getPayload().getAccount()
		);
	}
}
