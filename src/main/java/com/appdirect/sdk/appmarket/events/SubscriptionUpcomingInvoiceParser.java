package com.appdirect.sdk.appmarket.events;

import java.util.Map;

class SubscriptionUpcomingInvoiceParser implements EventParser<SubscriptionUpcomingInvoice> {
	@Override
	public SubscriptionUpcomingInvoice parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo, Map<String, String[]> queryParams) {
		return new SubscriptionUpcomingInvoice(consumerKeyUsedByTheRequest, eventInfo.getPayload().getAccount(), queryParams);
	}
}
