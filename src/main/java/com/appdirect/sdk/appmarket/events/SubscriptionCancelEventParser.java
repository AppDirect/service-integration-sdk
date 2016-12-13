package com.appdirect.sdk.appmarket.events;

class SubscriptionCancelEventParser implements EventParser<SubscriptionCancel> {
	@Override
	public SubscriptionCancel parse(EventInfo eventInfo, EventExecutionContext eventContext) {
		return new SubscriptionCancel(
				eventContext.getConsumerKeyUsedByTheRequest(),
				eventInfo.getPayload().getAccount().getAccountIdentifier(),
				eventContext.getQueryParameters()
		);
	}
}
