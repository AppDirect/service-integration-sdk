package com.appdirect.sdk.appmarket.events;

import lombok.Value;

/**
 * A developer-facing event representing cancellation of an account requested by the AppMarket
 */
@Value
public class SubscriptionCancel {
	private final String consumerKeyUsedByTheRequest;
	private final String accountIdentifier;
}
