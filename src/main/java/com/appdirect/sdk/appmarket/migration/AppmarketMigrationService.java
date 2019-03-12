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

package com.appdirect.sdk.appmarket.migration;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.appmarket.events.APIResult;

/**
 * Provides an interface for invoking the client-provided fieldsValidation logic for migrations.
 */
@Slf4j
@RequiredArgsConstructor
public class AppmarketMigrationService {
	private final CustomerAccountValidationHandler customerAccountValidationHandler;
	private final SubscriptionValidationHandler subscriptionValidationHandler;
	private final SubscriptionMigrationHandler subscriptionMigrationHandler;

	public APIResult validateCustomerAccount(Map<String, String> customerAccountData) {
		return customerAccountValidationHandler.validate(customerAccountData);
	}

	public APIResult validateSubscription(Map<String, String> subscriptionData) {
		return subscriptionValidationHandler.validate(subscriptionData);
	}

	public APIResult migrate(Subscription subscription) {
		return subscriptionMigrationHandler.handle(subscription);
	}
}
