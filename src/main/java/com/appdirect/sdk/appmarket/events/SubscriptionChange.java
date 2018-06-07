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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Developer-facing event representing updates to an account requested by the AppMarket
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SubscriptionChange extends EventWithContextWithConfiguration {
	private UserInfo owner;
	private OrderInfo order;
	private AccountInfo account;

	public SubscriptionChange(String consumerKeyUsedByTheRequest,
							  UserInfo owner,
							  OrderInfo order,
							  AccountInfo account,
							  Map<String, String> configuration,
							  Map<String, String[]> queryParameters,
							  EventFlag flag,
							  String eventToken,
							  String marketplaceUrl) {

		super(consumerKeyUsedByTheRequest, queryParameters, flag, eventToken, marketplaceUrl, configuration);
		this.owner = owner;
		this.order = order;
		this.account = account;
	}

	@Deprecated
	public SubscriptionChange(String consumerKeyUsedByTheRequest,
							  UserInfo owner,
							  OrderInfo order,
							  AccountInfo account,
							  Map<String, String[]> queryParameters,
							  EventFlag flag,
							  String eventToken,
							  String marketplaceUrl) {

		this(consumerKeyUsedByTheRequest, owner, order, account,
			 new HashMap<>(), queryParameters,flag, eventToken, marketplaceUrl);
	}
}
