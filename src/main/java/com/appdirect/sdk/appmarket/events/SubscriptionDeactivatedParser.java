package com.appdirect.sdk.appmarket.events;

class SubscriptionDeactivatedParser implements EventParser<SubscriptionDeactivated> {
	@Override
	public SubscriptionDeactivated parse(EventInfo eventInfo, EventExecutionContext eventContext) {
		return new SubscriptionDeactivated(eventContext.getConsumerKeyUsedByTheRequest(), eventInfo.getPayload().getAccount(), eventContext.getQueryParameters());
	}
}
