package com.appdirect.sdk.appmarket.migration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyMapOf;
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
		when(migrationService.validateCustomerAccount(anyMapOf(String.class, String.class))).thenReturn(APIResult.success("Success"));

		Callable<APIResult> result = migrationController.validateISVCustomerAccount(new HashMap<>());
		APIResult apiResult = result.call();

		assertThat(apiResult.isSuccess()).isTrue();
		assertThat(apiResult.getMessage()).isEqualTo("Success");
	}

	@Test
	public void validateISVCustomerAccount_failure() throws Exception {
		when(migrationService.validateCustomerAccount(anyMapOf(String.class, String.class))).thenReturn(APIResult.failure(ErrorCode.NOT_FOUND, "Validation failed."));

		Callable<APIResult> result = migrationController.validateISVCustomerAccount(new HashMap<>());
		APIResult apiResult = result.call();

		assertThat(apiResult.isSuccess()).isFalse();
		assertThat(apiResult.getMessage()).isEqualTo("Validation failed.");
	}

	@Test
	public void validateISVCustomerSubscription_success() throws Exception {
		when(migrationService.validateSubscription(anyMapOf(String.class, String.class))).thenReturn(APIResult.success("Success"));
		Callable<APIResult> result = migrationController.validateISVSubscription(new HashMap<>());
		APIResult apiResult = result.call();

		assertThat(apiResult.isSuccess()).isTrue();
		assertThat(apiResult.getMessage()).isEqualTo("Success");
	}

	@Test
	public void validateISVCustomerSubscription_failure() throws Exception {
		when(migrationService.validateSubscription(anyMapOf(String.class, String.class))).thenReturn(APIResult.failure(ErrorCode.CONFIGURATION_ERROR, "Failure in validation"));
		Callable<APIResult> result = migrationController.validateISVSubscription(new HashMap<>());
		APIResult apiResult = result.call();

		assertThat(apiResult.isSuccess()).isFalse();
		assertThat(apiResult.getMessage()).isEqualTo("Failure in validation");
	}
}
