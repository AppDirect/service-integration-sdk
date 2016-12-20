package com.appdirect.sdk.appmarket.events;

class SubscriptionClosedParser implements EventParser<SubscriptionClosed> {
	@Override
	public SubscriptionClosed parse(EventInfo eventInfo, EventExecutionContext eventContext) {
		return new SubscriptionClosed(eventContext.getConsumerKeyUsedByTheRequest(), eventInfo.getPayload().getAccount(), eventContext.getQueryParameters(), eventInfo.getFlag());
	}
}
