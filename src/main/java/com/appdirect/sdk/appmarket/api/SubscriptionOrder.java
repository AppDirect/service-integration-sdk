package com.appdirect.sdk.appmarket.api;

import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SubscriptionOrder implements Event {
	private final String consumerKeyUsedByTheRequest;
	private final EventFlag flag;
	private final UserInfo purchaserInfo;
	private final Map<String, String> configuration;
	private final CompanyInfo companyInfo;
	private final OrderInfo orderInfo;
	private final String partner;
	private final String applicationUuid;

	public SubscriptionOrder(String consumerKeyUsedByTheRequest, EventFlag flag, UserInfo purchaserInfo, Map<String, String> configuration, CompanyInfo companyInfo, OrderInfo orderInfo, String partner, String applicationUuid) {
		this.consumerKeyUsedByTheRequest = consumerKeyUsedByTheRequest;
		this.flag = flag;
		this.purchaserInfo = purchaserInfo;
		this.configuration = configuration;
		this.companyInfo = companyInfo;
		this.orderInfo = orderInfo;
		this.partner = partner;
		this.applicationUuid = applicationUuid;
	}

	public String getConsumerKeyUsedByTheRequest() {
		return consumerKeyUsedByTheRequest;
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

	public String getPartner() {
		return partner;
	}

	public Optional<String> getApplicationUuid() {
		return Optional.ofNullable(applicationUuid);
	}
}
