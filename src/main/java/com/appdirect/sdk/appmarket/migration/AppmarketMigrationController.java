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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Map;
import java.util.concurrent.Callable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.appmarket.events.ErrorCode;

/**
 * Defines the endpoint for operations related to importing existing accounts into the connector.
 * By "migration" we mean in
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AppmarketMigrationController {
	private final AppmarketMigrationService migrationService;

	/**
	 * Validate a customer account record that need to be migrated. It is meant to receive the
	 * input of a migration and indicate if it can be migrated.
	 *
	 * @param isvCustomerAccountData A single migration record that is to be validated
	 * @return an {@link APIResult#success(String)} in case the validation is successful and a {@link APIResult#failure(ErrorCode, String)}
	 * otherwise.
	 */
	@RequestMapping(method = POST, value = "/api/v1/migration/validateCustomerAccount", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public Callable<APIResult> validateISVCustomerAccount(@RequestBody Map<String, String> isvCustomerAccountData) {
		return () -> migrationService.validateCustomerAccount(isvCustomerAccountData);
	}

	/**
	 * Validate a subscription record that need to be migrated. It is meant to receive the
	 * input of a migration and indicate if it can be migrated.
	 *
	 * @param isvSubscriptionData the subscription record to be migrated
	 * @return an {@link APIResult#success(String)} in case the validation is successful and a {@link APIResult#failure(ErrorCode, String)}
	 * otherwise.
	 */
	@RequestMapping(method = POST, value = "/api/v1/migration/validateSubscription", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public Callable<APIResult> validateISVSubscription(@RequestBody Map<String, String> isvSubscriptionData) {
		return () -> migrationService.validateSubscription(isvSubscriptionData);
	}

	/**
	 * Migrate a subscription which is already validated by {@link AppmarketMigrationController#validateISVSubscription(Map)}
	 *
	 * @param subscription the subscription record to be migrated
	 * @return an {@link APIResult#success(String)} in case the migration is successful and a {@link APIResult#failure(ErrorCode, String)}
	 */
	@RequestMapping(method = POST, value = "/api/v1/migration/subscription", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public Callable<APIResult> migrate(@RequestBody Subscription subscription) {
		return () -> migrationService.migrate(subscription);
	}

	/**
	 * Validate a customer account record that need to be migrated. It is meant to receive the
	 * input of a migration and indicate if it can be migrated.
	 *
	 * @param isvCustomerAccountData A single migration record that is to be validated
	 * @return an {@link APIResult#success(String)} in case the validation is successful and a {@link APIResult#failure(ErrorCode, String)}
	 * otherwise.
	 */
	@RequestMapping(method = POST, value = "/api/v2/migration/validateCustomerAccount", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public Callable<APIResult> validateISVCustomerAccountV2(@RequestBody Map<String, String> isvCustomerAccountData) {
		return () -> migrationService.validateCustomerAccount(isvCustomerAccountData);
	}

	/**
	 * Validate a subscription record that need to be migrated. It is meant to receive the
	 * input of a migration and indicate if it can be migrated.
	 *
	 * @param isvSubscriptionData the subscription record to be migrated
	 * @return an {@link APIResult#success(String)} in case the validation is successful and a {@link APIResult#failure(ErrorCode, String)}
	 * otherwise.
	 */
	@RequestMapping(method = POST, value = "/api/v2/migration/validateSubscription", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public Callable<APIResult> validateISVSubscriptionV2(@RequestBody Map<String, String> isvSubscriptionData) {
		return () -> migrationService.validateSubscription(isvSubscriptionData);
	}

	/**
	 * Migrate a subscription which is already validated by {@link AppmarketMigrationController#validateISVSubscription(Map)}
	 *
	 * @param subscription the subscription record to be migrated
	 * @return an {@link APIResult#success(String)} in case the migration is successful and a {@link APIResult#failure(ErrorCode, String)}
	 */
	@RequestMapping(method = POST, value = "/api/v2/migration/subscription", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public Callable<APIResult> migrateV2(@RequestBody Subscription subscription) {
		return () -> migrationService.migrate(subscription);
	}
}
