package com.appdirect.sdk;

import lombok.Value;

@Value
public class SubscriptionChange {
	private final String consumerKeyUsedByRequest;
	private final UserInfo owner;
	private final OrderInfo order;
	private final AccountInfo account;
}
