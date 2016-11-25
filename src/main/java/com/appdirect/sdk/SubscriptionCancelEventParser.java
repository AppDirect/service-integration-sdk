package com.appdirect.sdk;

class SubscriptionCancelEventParser implements EventParser<SubscriptionCancel> {
	@Override
	public SubscriptionCancel parse(String consumerKeyUsedByTheRequest, EventInfo eventInfo) {
		return new SubscriptionCancel(
				consumerKeyUsedByTheRequest,
				eventInfo.getPayload().getAccount().getAccountIdentifier()
		);
	}
}
