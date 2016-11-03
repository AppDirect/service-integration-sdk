package com.appdirect.sdk.appmarket.api;

import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SubscriptionOrder implements Event {
	private final EventFlag flag;
	private final UserInfo purchaser;
	private final Map<String, String> configuration;
	private final CompanyInfo company;
	private final OrderInfo orderInfo;

	public SubscriptionOrder(EventFlag flag, UserInfo purchaser, Map<String, String> configuration, CompanyInfo company, OrderInfo orderInfo) {
		this.flag = flag;
		this.purchaser = purchaser;
		this.configuration = configuration;
		this.company = company;
		this.orderInfo = orderInfo;
	}

	public Optional<EventFlag> getFlag() {
		return ofNullable(flag);
	}

	public UserInfo getPurchaser() {
		return purchaser;
	}

	public Map<String, String> getConfiguration() {
		return new HashMap<>(configuration);
	}

	public CompanyInfo getCompany() {
		return company;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}
}
