package com.appdirect.sdk.appmarket.events;

class SubscriptionChangeEventParser implements EventParser<SubscriptionChange> {

	@Override
	public SubscriptionChange parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		return new SubscriptionChange(
				consumerKeyUsedByTheRequest,
				eventInfo.getCreator(),
				eventInfo.getPayload().getOrder(),
				eventInfo.getPayload().getAccount()
		);
	}
}
