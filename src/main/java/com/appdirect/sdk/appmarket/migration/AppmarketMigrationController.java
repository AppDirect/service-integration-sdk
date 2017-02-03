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

@Slf4j
@RestController
@RequiredArgsConstructor
public class AppmarketMigrationController {
	private final AppmarketMigrationService migrationService;

	@RequestMapping(method = POST, value = "/api/v1/migration/validateCustomerAccount", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public Callable<APIResult> validateISVCustomerAccount(@RequestBody Map<String, String> isvCustomerAccountData) {
		return () -> migrationService.validateCustomerAccount(isvCustomerAccountData);
	}
}
