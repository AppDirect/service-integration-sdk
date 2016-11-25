package com.appdirect.sdk.appmarket.api;

class SubscriptionReactivatedParser implements EventParser<SubscriptionReactivated> {
	@Override
	public SubscriptionReactivated parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		return new SubscriptionReactivated(eventInfo.getPayload().getAccount());
	}
}
