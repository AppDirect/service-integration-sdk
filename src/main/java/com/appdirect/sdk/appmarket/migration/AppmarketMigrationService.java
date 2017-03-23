package com.appdirect.sdk.appmarket.migration;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.appmarket.events.APIResult;

/**
 * Provides an interface for invoking the client-provided validation logic for migrations.
 */
@Slf4j
@RequiredArgsConstructor
public class AppmarketMigrationService {
	private final CustomerAccountValidationHandler customerAccountValidationHandler;
	private final SubscriptionValidationHandler subscriptionValidationHandler;

	public APIResult validateCustomerAccount(Map<String, String> customerAccountData) {
		return customerAccountValidationHandler.validate(customerAccountData);
	}

	public APIResult validateSubscription(Map<String, String> subscriptionData) {
		return subscriptionValidationHandler.validate(subscriptionData);
	}
}
