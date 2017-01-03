package com.appdirect.sdk.appmarket.events;

import java.util.Map;

import lombok.EqualsAndHashCode;

/**
 * Represents one of the subtypes of the Subscription Notice event sent by the App Market.
 * See the documentation at the link below for more detailed information regarding the significance of the event.
 *
 * @see <a href="https://docs.appdirect.com/developer/distribution/event-notifications/subscription-events#notice-types">SUBSCRIPTION_NOTICE types</a>
 */
@EqualsAndHashCode(callSuper = true)
public class SubscriptionReactivated extends EventWithConsumerKeyQueryParametersAndEventFlag {
	private final AccountInfo accountInfo;

	public SubscriptionReactivated(String consumerKeyUsedByTheRequest, AccountInfo accountInfo, Map<String, String[]> queryParameters, EventFlag flag) {
		super(consumerKeyUsedByTheRequest, queryParameters, flag);
		this.accountInfo = accountInfo;
	}

	public AccountInfo getAccountInfo() {
		return accountInfo;
	}
}
