package com.appdirect.sdk.appmarket.events;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Developer-facing event representing updates to an account requested by the AppMarket
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class SubscriptionChange extends EventWithContext {
	private final UserInfo owner;
	private final OrderInfo order;
	private final AccountInfo account;

	public SubscriptionChange(String consumerKeyUsedByTheRequest,
							  UserInfo owner,
							  OrderInfo order,
							  AccountInfo account,
							  Map<String, String[]> queryParameters,
							  EventFlag flag,
							  String eventToken,
							  String marketplaceUrl) {

		super(consumerKeyUsedByTheRequest, queryParameters, flag, eventToken, marketplaceUrl);
		this.owner = owner;
		this.order = order;
		this.account = account;
	}
}
