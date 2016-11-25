package com.appdirect.sdk;

class SubscriptionClosedParser implements EventParser<SubscriptionClosed> {
	@Override
	public SubscriptionClosed parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		return new SubscriptionClosed(eventInfo.getPayload().getAccount());
	}
}
