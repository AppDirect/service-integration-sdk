package com.appdirect.sdk.appmarket.events;

class AddonSubscriptionOrderEventParser implements EventParser<AddonSubscriptionOrder> {
	@Override
	public AddonSubscriptionOrder parse(EventInfo eventInfo, EventHandlingContext eventContext) {
		return new AddonSubscriptionOrder(
				eventContext.getConsumerKeyUsedByTheRequest(),
				eventInfo.getFlag(),
				eventInfo.getCreator(),
				eventInfo.getPayload().getCompany(),
				eventInfo.getPayload().getOrder(),
				eventInfo.getMarketplace().getPartner(),
				eventInfo.getPayload().getAccount().getParentAccountIdentifier(),
				eventContext.getQueryParameters());
	}
}
