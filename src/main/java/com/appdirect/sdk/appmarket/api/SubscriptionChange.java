package com.appdirect.sdk.appmarket.api;

import lombok.Value;

/**
 * Developer-facing event representing updates to an account requested by the AppMarket
 */
@Value
public class SubscriptionChange {
	private final String consumerKeyUsedByRequest;
	private final UserInfo owner;
	private final OrderInfo order;
	private final AccountInfo account;
}
