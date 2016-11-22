package com.appdirect.sdk.appmarket.api;

/**
 * Represents one of the subtypes of the Subscription Notice event sent by the App Market
 * See the documentation at the link below for more detailed information regarding the significance of the event 
 * @see <a href="https://docs.appdirect.com/developer/distribution/event-notifications/subscription-events#notice-types">SUBSCRIPTION_NOTICE types</a>
 * 
 */
public class SubscriptionClosed {
	private final AccountInfo accountInfo;

	public SubscriptionClosed(AccountInfo accountInfo) {

		this.accountInfo = accountInfo;
	}

	public AccountInfo getAccountInfo() {
		return accountInfo;
	}
}
