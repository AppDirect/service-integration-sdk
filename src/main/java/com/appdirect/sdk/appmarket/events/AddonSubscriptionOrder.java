package com.appdirect.sdk.appmarket.events;

import java.util.Map;

import lombok.Getter;

/**
 * Developer-facing event representing a SUBSCRIPTION_ORDER made for an add-on to the main product.
 */
@Getter
public class AddonSubscriptionOrder extends EventWithConsumerKeyQueryParametersAndEventFlag {
	private final UserInfo purchaserInfo;
	private final CompanyInfo companyInfo;
	private final OrderInfo orderInfo;
	private final String partner;
	private final String parentAccountIdentifier;

	public AddonSubscriptionOrder(String consumerKeyUsedByTheRequest, EventFlag flag, UserInfo purchaserInfo, CompanyInfo companyInfo, OrderInfo orderInfo, String partner, String parentAccountIdentifier, Map<String, String[]> queryParameters) {
		super(consumerKeyUsedByTheRequest, queryParameters, flag);
		this.purchaserInfo = purchaserInfo;
		this.companyInfo = companyInfo;
		this.orderInfo = orderInfo;
		this.partner = partner;
		this.parentAccountIdentifier = parentAccountIdentifier;
	}
}
