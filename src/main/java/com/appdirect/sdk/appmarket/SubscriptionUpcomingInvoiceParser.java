package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.SubscriptionUpcomingInvoice;

class SubscriptionUpcomingInvoiceParser implements EventParser<SubscriptionUpcomingInvoice> {
	@Override
	public SubscriptionUpcomingInvoice parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		return new SubscriptionUpcomingInvoice(eventInfo.getPayload().getAccount());
	}
}
