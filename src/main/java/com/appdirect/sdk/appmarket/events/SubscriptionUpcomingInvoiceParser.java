package com.appdirect.sdk.appmarket.events;

class SubscriptionUpcomingInvoiceParser implements EventParser<SubscriptionUpcomingInvoice> {
	@Override
	public SubscriptionUpcomingInvoice parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		return new SubscriptionUpcomingInvoice(eventInfo.getPayload().getAccount());
	}
}
