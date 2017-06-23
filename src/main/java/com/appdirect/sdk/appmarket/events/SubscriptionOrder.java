/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appdirect.sdk.appmarket.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Developer-facing event creation of an account requested by the AppMarket
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SubscriptionOrder extends EventWithContext {
	private UserInfo purchaserInfo;
	private Map<String, String> configuration;
	private CompanyInfo companyInfo;
	private OrderInfo orderInfo;
	private String partner;
	private String applicationUuid;

	public SubscriptionOrder(String consumerKeyUsedByTheRequest,
							 EventFlag flag,
							 UserInfo purchaserInfo,
							 Map<String, String> configuration,
							 CompanyInfo companyInfo,
							 OrderInfo orderInfo,
							 String partner,
							 String applicationUuid,
							 Map<String, String[]> queryParameters,
							 String eventToken,
							 String marketplaceUrl) { // NOSONAR: constructor is too big, but it's mostly just for sdk use

		super(consumerKeyUsedByTheRequest, queryParameters, flag, eventToken, marketplaceUrl);
		this.purchaserInfo = purchaserInfo;
		this.configuration = configuration;
		this.companyInfo = companyInfo;
		this.orderInfo = orderInfo;
		this.partner = partner;
		this.applicationUuid = applicationUuid;
	}

	public Map<String, String> getConfiguration() {
		return new HashMap<>(configuration);
	}

	public Optional<String> getApplicationUuid() {
		return Optional.ofNullable(applicationUuid);
	}
}
