package com.appdirect.sdk.appmarket;

import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.SubscriptionCancel;

@Slf4j
class SubscriptionCancelEventParser implements EventParser<SubscriptionCancel> {
	@Override
	public SubscriptionCancel parse(EventInfo event) {

		log.info("Parsing event of type {}", event.getType());
		return new SubscriptionCancel(
			event.getPayload().getAccount().getAccountIdentifier(),
			event.getMarketplace()
		);
	}
}
