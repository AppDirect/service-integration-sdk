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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.concurrent.Callable;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.appmarket.events.ErrorCode;

public class AppmarketMigrationControllerTest {
	@Mock
	private AppmarketMigrationService migrationService;
	private AppmarketMigrationController migrationController;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		migrationController = new AppmarketMigrationController(migrationService);
	}

	@Test
	public void validateISVCustomerAccount_success() throws Exception {
		when(migrationService.validateCustomerAccount(anyMap())).thenReturn(APIResult.success("Success"));

		Callable<APIResult> result = migrationController.validateISVCustomerAccount(new HashMap<>());
		APIResult apiResult = result.call();

		assertThat(apiResult.isSuccess()).isTrue();
		assertThat(apiResult.getMessage()).isEqualTo("Success");
	}

	@Test
	public void validateISVCustomerAccount_failure() throws Exception {
		when(migrationService.validateCustomerAccount(anyMap())).thenReturn(APIResult.failure(ErrorCode.NOT_FOUND, "Validation failed."));

		Callable<APIResult> result = migrationController.validateISVCustomerAccount(new HashMap<>());
		APIResult apiResult = result.call();

		assertThat(apiResult.isSuccess()).isFalse();
		assertThat(apiResult.getMessage()).isEqualTo("Validation failed.");
	}

	@Test
	public void validateISVCustomerSubscription_success() throws Exception {
		when(migrationService.validateSubscription(anyMap())).thenReturn(APIResult.success("Success"));
		Callable<APIResult> result = migrationController.validateISVSubscription(new HashMap<>());
		APIResult apiResult = result.call();

		assertThat(apiResult.isSuccess()).isTrue();
		assertThat(apiResult.getMessage()).isEqualTo("Success");
	}

	@Test
	public void validateISVCustomerSubscription_failure() throws Exception {
		when(migrationService.validateSubscription(anyMap())).thenReturn(APIResult.failure(ErrorCode.CONFIGURATION_ERROR, "Failure in validation"));
		Callable<APIResult> result = migrationController.validateISVSubscription(new HashMap<>());
		APIResult apiResult = result.call();

		assertThat(apiResult.isSuccess()).isFalse();
		assertThat(apiResult.getMessage()).isEqualTo("Failure in validation");
	}

	@Test
	public void migrateISVSubscription_success() throws Exception {
		when(migrationService.migrate(any(Subscription.class))).thenReturn(APIResult.success("Success"));
		Callable<APIResult> result = migrationController.migrate(new Subscription());
		APIResult apiResult = result.call();

		assertThat(apiResult.isSuccess()).isTrue();
		assertThat(apiResult.getMessage()).isEqualTo("Success");
	}

	@Test
	public void migrationISVSubscription_failure() throws Exception {
		when(migrationService.migrate(any(Subscription.class))).thenReturn(APIResult.failure(ErrorCode.CONFIGURATION_ERROR, "Migration Failed"));
		Callable<APIResult> result = migrationController.migrate(new Subscription());
		APIResult apiResult = result.call();

		assertThat(apiResult.isSuccess()).isFalse();
		assertThat(apiResult.getMessage()).isEqualTo("Migration Failed");
	}
}
