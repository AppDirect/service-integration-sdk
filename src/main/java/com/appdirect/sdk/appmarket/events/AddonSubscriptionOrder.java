package com.appdirect.sdk.appmarket.events;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Developer-facing event representing a SUBSCRIPTION_ORDER made for an add-on to the main product.
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class AddonSubscriptionOrder extends EventWithContext {
	private final UserInfo purchaserInfo;
	private final CompanyInfo companyInfo;
	private final OrderInfo orderInfo;
	private final String partner;
	private final String parentAccountIdentifier;

	public AddonSubscriptionOrder(String consumerKeyUsedByTheRequest,
								  EventFlag flag,
								  UserInfo purchaserInfo,
								  CompanyInfo companyInfo,
								  OrderInfo orderInfo,
								  String partner,
								  String parentAccountIdentifier,
								  Map<String, String[]> queryParameters,
								  String eventId) { // NOSONAR: constructor is too big, but it's mostly just for sdk use

		super(consumerKeyUsedByTheRequest, queryParameters, flag, eventId);
		this.purchaserInfo = purchaserInfo;
		this.companyInfo = companyInfo;
		this.orderInfo = orderInfo;
		this.partner = partner;
		this.parentAccountIdentifier = parentAccountIdentifier;
	}
}
