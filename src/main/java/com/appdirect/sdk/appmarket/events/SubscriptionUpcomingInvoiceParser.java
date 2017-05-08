package com.appdirect.sdk.appmarket.events;

class SubscriptionUpcomingInvoiceParser implements EventParser<SubscriptionUpcomingInvoice> {
	@Override
	public SubscriptionUpcomingInvoice parse(EventInfo eventInfo, EventHandlingContext eventContext) {
		return new SubscriptionUpcomingInvoice(
				eventContext.getConsumerKeyUsedByTheRequest(),
				eventInfo.getPayload().getAccount(),
				eventContext.getQueryParameters(),
				eventInfo.getFlag(),
				eventInfo.getId()
		);
	}
}
