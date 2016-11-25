package com.appdirect.sdk.appmarket.api;

class SubscriptionDeactivatedParser implements EventParser<SubscriptionDeactivated> {
	@Override
	public SubscriptionDeactivated parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		return new SubscriptionDeactivated(eventInfo.getPayload().getAccount());
	}
}
