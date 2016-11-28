package com.appdirect.sdk.appmarket.events;

class SubscriptionClosedParser implements EventParser<SubscriptionClosed> {
	@Override
	public SubscriptionClosed parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		return new SubscriptionClosed(eventInfo.getPayload().getAccount());
	}
}
