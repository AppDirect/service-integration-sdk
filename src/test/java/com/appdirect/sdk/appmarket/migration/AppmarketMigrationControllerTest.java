package com.appdirect.sdk.appmarket.migration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyMap;
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
}
