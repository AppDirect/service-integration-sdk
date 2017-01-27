package com.appdirect.sdk.appmarket.migration;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.appmarket.events.APIResult;

@Slf4j
@RequiredArgsConstructor
public class AppmarketMigrationService {
	private final CustomerAccountValidator customerAccountValidator;

	public APIResult validateCustomerAccount(Map<String, String> customerAccountData) {
		return customerAccountValidator.validate(customerAccountData);
	}
}
