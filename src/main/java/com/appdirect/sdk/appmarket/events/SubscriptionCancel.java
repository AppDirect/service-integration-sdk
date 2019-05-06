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
 * A developer-facing event representing cancellation of an account requested by the AppMarket
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SubscriptionCancel extends EventWithContextWithConfiguration {
	private String accountIdentifier;

	public SubscriptionCancel(String consumerKeyUsedByTheRequest,
							  String accountIdentifier,
							  Map<String, String[]> queryParameters,
							  EventFlag flag,
							  String eventToken,
							  String marketplaceUrl,
							  Map<String, String> configuration) {

		super(consumerKeyUsedByTheRequest, queryParameters, flag, eventToken, marketplaceUrl, configuration);
		this.accountIdentifier = accountIdentifier;
	}

	@Deprecated
	public SubscriptionCancel(String consumerKeyUsedByTheRequest,
							  String accountIdentifier,
							  Map<String, String[]> queryParameters,
							  EventFlag flag,
							  String eventToken,
							  String marketplaceUrl) {

		this(consumerKeyUsedByTheRequest, accountIdentifier, queryParameters,
		    flag, eventToken, marketplaceUrl, new HashMap<>());
	}
}
