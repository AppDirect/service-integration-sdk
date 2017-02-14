package com.appdirect.sdk.appmarket.migration;

import org.springframework.context.annotation.Bean;

import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.appmarket.events.ErrorCode;

public class DefaultMigrationHandlers {
	@Bean
	public CustomerAccountValidationHandler customerAccountValidatorHandler() {
		return (customerAccountData) -> APIResult.failure(ErrorCode.CONFIGURATION_ERROR, "Customer account validation is not supported.");
	}

	@Bean
	public SubscriptionValidationHandler subscriptionValidatorHandler() {
		return (subscriptionData) -> APIResult.failure(ErrorCode.CONFIGURATION_ERROR, "Subscription validation is not supported.");
	}
}
