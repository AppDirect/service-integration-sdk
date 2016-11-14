package com.appdirect.sdk.appmarket.api;

import lombok.Value;

@Value
public class SubscriptionChange {
	private UserInfo owner;
	private OrderInfo order;
	private AccountInfo account;
}
