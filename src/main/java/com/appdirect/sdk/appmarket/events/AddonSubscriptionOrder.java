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

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Developer-facing event representing a SUBSCRIPTION_ORDER made for an add-on to the main product.
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AddonSubscriptionOrder extends EventWithContextWithConfiguration {
	private UserInfo purchaserInfo;
	private CompanyInfo companyInfo;
	private OrderInfo orderInfo;
	private String partner;
	private String parentAccountIdentifier;

	public AddonSubscriptionOrder(String consumerKeyUsedByTheRequest,
								  EventFlag flag,
								  UserInfo purchaserInfo,
								  CompanyInfo companyInfo,
								  OrderInfo orderInfo,
								  String partner,
								  String parentAccountIdentifier,
								  Map<String, String[]> queryParameters,
								  String eventToken,
								  String marketplaceUrl,
								  Map<String, String> configuration) { // NOSONAR: constructor is too big, but it's mostly just for sdk use

		super(consumerKeyUsedByTheRequest, queryParameters, flag, eventToken, marketplaceUrl, configuration);
		this.purchaserInfo = purchaserInfo;
		this.companyInfo = companyInfo;
		this.orderInfo = orderInfo;
		this.partner = partner;
		this.parentAccountIdentifier = parentAccountIdentifier;
	}

	@Deprecated
	public AddonSubscriptionOrder(String consumerKeyUsedByTheRequest,
								  EventFlag flag,
								  UserInfo purchaserInfo,
								  CompanyInfo companyInfo,
								  OrderInfo orderInfo,
								  String partner,
								  String parentAccountIdentifier,
								  Map<String, String[]> queryParameters,
								  String eventToken,
								  String marketplaceUrl) { // NOSONAR: constructor is too big, but it's mostly just for sdk use

		super(consumerKeyUsedByTheRequest, queryParameters, flag, eventToken, marketplaceUrl, new HashMap<>());
		this.purchaserInfo = purchaserInfo;
		this.companyInfo = companyInfo;
		this.orderInfo = orderInfo;
		this.partner = partner;
		this.parentAccountIdentifier = parentAccountIdentifier;
	}
}
