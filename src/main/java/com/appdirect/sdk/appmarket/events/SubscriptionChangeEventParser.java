package com.appdirect.sdk.appmarket.events;

class SubscriptionChangeEventParser implements EventParser<SubscriptionChange> {

	@Override
	public SubscriptionChange parse(EventInfo eventInfo, EventHandlingContext eventContext) {
		return new SubscriptionChange(
				eventContext.getConsumerKeyUsedByTheRequest(),
				eventInfo.getCreator(),
				eventInfo.getPayload().getOrder(),
				eventInfo.getPayload().getAccount(),
				eventContext.getQueryParameters(),
				eventInfo.getFlag(),
				eventInfo.getId(),
				eventInfo.getMarketplace().getBaseUrl()
		);
	}
}
