package com.appdirect.sdk.appmarket.migration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.appmarket.events.APIResult;

@Slf4j
@RequiredArgsConstructor
public class AppmarketMigrationService {
	private final CustomerAccountValidationHandler customerAccountValidator;

	public APIResult validateCustomerAccount(CustomerAccount customerAccount) {
		return customerAccountValidator.validate(customerAccount);
	}
}
