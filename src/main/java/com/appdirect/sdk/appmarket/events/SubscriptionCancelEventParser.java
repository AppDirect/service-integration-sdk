package com.appdirect.sdk.appmarket.events;

class SubscriptionCancelEventParser implements EventParser<SubscriptionCancel> {
	@Override
	public SubscriptionCancel parse(EventInfo eventInfo, EventHandlingContext eventContext) {
		return new SubscriptionCancel(
				eventContext.getConsumerKeyUsedByTheRequest(),
				eventInfo.getPayload().getAccount().getAccountIdentifier(),
				eventContext.getQueryParameters(),
				eventInfo.getFlag(),
				eventInfo.getId(),
				eventInfo.getMarketplace().getBaseUrl()
		);
	}
}
