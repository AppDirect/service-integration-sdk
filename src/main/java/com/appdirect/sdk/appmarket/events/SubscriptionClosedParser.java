package com.appdirect.sdk.appmarket.events;

class SubscriptionClosedParser implements EventParser<SubscriptionClosed> {
	@Override
	public SubscriptionClosed parse(EventInfo eventInfo, EventHandlingContext eventContext) {
		return new SubscriptionClosed(
				eventContext.getConsumerKeyUsedByTheRequest(),
				eventInfo.getPayload().getAccount(),
				eventContext.getQueryParameters(),
				eventInfo.getFlag(),
				eventInfo.getId(),
				eventInfo.getMarketplace().getBaseUrl()
		);
	}
}
