package com.appdirect.sdk.appmarket.events;

class AddonSubscriptionCancelEventParser implements EventParser<AddonSubscriptionCancel> {
	@Override
	public AddonSubscriptionCancel parse(EventInfo eventInfo, EventHandlingContext eventContext) {
		return new AddonSubscriptionCancel(
			eventInfo.getPayload().getAccount().getAccountIdentifier(),
			eventInfo.getPayload().getAccount().getParentAccountIdentifier(),
			eventContext.getConsumerKeyUsedByTheRequest(),
			eventContext.getQueryParameters(),
			eventInfo.getFlag(),
			eventInfo.getId(),
			eventInfo.getMarketplace().getBaseUrl()
		);
	}
}
