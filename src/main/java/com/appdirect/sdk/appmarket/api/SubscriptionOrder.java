package com.appdirect.sdk.appmarket.api;

import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SubscriptionOrder implements Event {
	private final EventFlag flag;
	private final UserInfo purchaserInfo;
	private final Map<String, String> configuration;
	private final CompanyInfo companyInfo;
	private final OrderInfo orderInfo;

	public SubscriptionOrder(EventFlag flag, UserInfo purchaserInfo, Map<String, String> configuration, CompanyInfo companyInfo, OrderInfo orderInfo) {
		this.flag = flag;
		this.purchaserInfo = purchaserInfo;
		this.configuration = configuration;
		this.companyInfo = companyInfo;
		this.orderInfo = orderInfo;
	}

	public Optional<EventFlag> getFlag() {
		return ofNullable(flag);
	}

	public UserInfo getPurchaserInfo() {
		return purchaserInfo;
	}

	public Map<String, String> getConfiguration() {
		return new HashMap<>(configuration);
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}
}
