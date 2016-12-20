package com.appdirect.sdk.appmarket.events;

class SubscriptionChangeEventParser implements EventParser<SubscriptionChange> {

	@Override
	public SubscriptionChange parse(EventInfo eventInfo, EventExecutionContext eventContext) {
		return new SubscriptionChange(
				eventContext.getConsumerKeyUsedByTheRequest(),
				eventInfo.getCreator(),
				eventInfo.getPayload().getOrder(),
				eventInfo.getPayload().getAccount(),
				eventContext.getQueryParameters(),
				null
		);
	}
}
