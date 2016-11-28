package com.appdirect.sdk.appmarket.events;

/**
 * Represents one of the subtypes of the Subscription Notice event sent by the App Market.
 * See the documentation at the link below for more detailed information regarding the significance of the event.
 * <p>
 * Notice that this event is similar to {@link SubscriptionDeactivated}, with the major difference being that
 * when a {@link SubscriptionClosed} is received, the data stored in the developer application regarding the closed
 * account can be safely deleted, since the account can no longer be reactivated. As the documentation below mentions,
 * this event is most likely to be handled the same way as a {@link SubscriptionCancel} event.
 *
 * @see <a href="https://docs.appdirect.com/developer/distribution/event-notifications/subscription-events#notice-types">SUBSCRIPTION_NOTICE types</a>
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
