package com.appdirect.sdk.appmarket.events;

class SubscriptionReactivatedParser implements EventParser<SubscriptionReactivated> {
	@Override
	public SubscriptionReactivated parse(EventInfo eventInfo, EventHandlingContext eventContext) {
		return new SubscriptionReactivated(
				eventContext.getConsumerKeyUsedByTheRequest(), 
				eventInfo.getPayload().getAccount(), 
				eventContext.getQueryParameters(), 
				eventInfo.getFlag(),
				eventInfo.getId()
		);
	}
}
