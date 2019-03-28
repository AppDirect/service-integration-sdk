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

import org.springframework.context.annotation.Bean;

import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.appmarket.events.ErrorCode;

public class DefaultMigrationHandlers {
	@Bean
	public CustomerAccountValidationHandler customerAccountValidatorHandler() {
		return (customerAccountData) -> APIResult.failure(ErrorCode.CONFIGURATION_ERROR, "Customer account fieldsValidation is not supported.");
	}

	@Bean
	public SubscriptionValidationHandler subscriptionValidatorHandler() {
		return subscriptionData -> APIResult.failure(ErrorCode.CONFIGURATION_ERROR, "Subscription fieldsValidation is not supported.");
	}

	@Bean
	public SubscriptionMigrationHandler subscriptionMigrationHandler() {
		return subscription -> APIResult.failure(ErrorCode.CONFIGURATION_ERROR, "Subscription migration is not supported.");
	}
}
