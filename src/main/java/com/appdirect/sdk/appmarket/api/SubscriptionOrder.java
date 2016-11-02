package com.appdirect.sdk.appmarket.api;

import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SubscriptionOrder implements Event {
	private final Map<String, String> configuration;
	private final EventFlag flag;
	private final UserInfo purchaser;
	private final CompanyInfo company;

	public SubscriptionOrder(Map<String, String> configuration, EventFlag flag, UserInfo purchaser, CompanyInfo company) {
		this.configuration = configuration;
		this.flag = flag;
		this.purchaser = purchaser;
		this.company = company;
	}

	public Map<String, String> getConfiguration() {
		return new HashMap<>(configuration);
	}

	public Optional<EventFlag> getFlag() {
		return ofNullable(flag);
	}

	public UserInfo getPurchaser() {
		return purchaser;
	}

	public CompanyInfo getCompany() {
		return company;
	}
}
