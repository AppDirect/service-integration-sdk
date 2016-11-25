package com.appdirect.sdk;

import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubscriptionOrder {
	private final String consumerKeyUsedByTheRequest;
	private final EventFlag flag;
	private final UserInfo purchaserInfo;
	private final Map<String, String> configuration;
	private final CompanyInfo companyInfo;
	private final OrderInfo orderInfo;
	private final String partner;
	private final String applicationUuid;

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
