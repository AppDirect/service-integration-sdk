package com.appdirect.sdk.appmarket.events;

/**
 * To see what is mandatory or not, consult https://docs.appdirect.com/developer/distribution/event-notifications/subscription-events#attributes
 */
class SubscriptionOrderEventParser implements EventParser<SubscriptionOrder> {
	@Override
	public SubscriptionOrder parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		return new SubscriptionOrder(
				consumerKeyUsedByTheRequest,
				eventInfo.getFlag(),
				eventInfo.getCreator(),
				eventInfo.getPayload().getConfiguration(),
				eventInfo.getPayload().getCompany(),
				eventInfo.getPayload().getOrder(),
				eventInfo.getMarketplace().getPartner(),
				eventInfo.getApplicationUuid());
	}
}
