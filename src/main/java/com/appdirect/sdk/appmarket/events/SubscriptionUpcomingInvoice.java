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
 * Represents one of the subtypes of the Subscription Notice event sent by the App Market.
 * See the documentation at the link below for more detailed information regarding the significance of the event.
 *
 * @see <a href="https://docs.appdirect.com/developer/distribution/event-notifications/subscription-events#notice-types">SUBSCRIPTION_NOTICE types</a>
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@NoArgsConstructor
public class SubscriptionUpcomingInvoice extends EventWithContextWithConfiguration {
	private AccountInfo accountInfo;

	public SubscriptionUpcomingInvoice(String consumerKeyUsedByTheRequest,
									   AccountInfo accountInfo,
									   Map<String, String[]> queryParameters,
									   EventFlag flag,
									   String eventToken,
									   String marketplaceUrl,
									   Map<String, String> configuration) {

		super(consumerKeyUsedByTheRequest, queryParameters, flag, eventToken, marketplaceUrl, configuration);
		this.accountInfo = accountInfo;
	}

	@Deprecated
	public SubscriptionUpcomingInvoice(String consumerKeyUsedByTheRequest,
									   AccountInfo accountInfo,
									   Map<String, String[]> queryParameters,
									   EventFlag flag,
									   String eventToken,
									   String marketplaceUrl) {

		this(consumerKeyUsedByTheRequest, accountInfo, queryParameters,
			 flag, eventToken, marketplaceUrl, new HashMap<>());
	}
}
