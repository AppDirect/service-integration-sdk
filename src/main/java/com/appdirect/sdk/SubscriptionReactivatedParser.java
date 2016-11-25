package com.appdirect.sdk;

class SubscriptionReactivatedParser implements EventParser<SubscriptionReactivated> {
	@Override
	public SubscriptionReactivated parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		return new SubscriptionReactivated(eventInfo.getPayload().getAccount());
	}
}
