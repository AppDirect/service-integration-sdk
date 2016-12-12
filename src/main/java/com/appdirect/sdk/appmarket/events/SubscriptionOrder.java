package com.appdirect.sdk.appmarket.events;

import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;

/**
 * Developer-facing event creation of an account requested by the AppMarket
 */
@Getter
public class SubscriptionOrder extends EventWithConsumerKeyAndQueryParameters {
	private final EventFlag flag;
	private final UserInfo purchaserInfo;
	private final Map<String, String> configuration;
	private final CompanyInfo companyInfo;
	private final OrderInfo orderInfo;
	private final String partner;
	private final String applicationUuid;

	public SubscriptionOrder(String consumerKeyUsedByTheRequest, EventFlag flag, UserInfo purchaserInfo, Map<String, String> configuration, CompanyInfo companyInfo, OrderInfo orderInfo, String partner, String applicationUuid, Map<String, String[]> queryParameters) { // NOSONAR: constructor is too big, but it's mostly just for sdk use
		super(consumerKeyUsedByTheRequest, queryParameters);
		this.flag = flag;
		this.purchaserInfo = purchaserInfo;
		this.configuration = configuration;
		this.companyInfo = companyInfo;
		this.orderInfo = orderInfo;
		this.partner = partner;
		this.applicationUuid = applicationUuid;
	}

	public Optional<EventFlag> getFlag() {
		return ofNullable(flag);
	}

	public Map<String, String> getConfiguration() {
		return new HashMap<>(configuration);
	}

	public Optional<String> getApplicationUuid() {
		return Optional.ofNullable(applicationUuid);
	}
}
