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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.appdirect.sdk.meteredusage.model.CustomAttribute;

/**
 * Contains all relevant information of an order placed using a {@link SubscriptionOrder} event
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class OrderInfo implements Serializable {
	private String editionCode;
	private String addonOfferingCode;
	private PricingDuration pricingDuration;
	private List<OrderItemInfo> items = new ArrayList<>();
	private List<CustomAttribute> customAttributes;
	private String discountCode;
	private FreeTrialInfo freeTrial;
}
