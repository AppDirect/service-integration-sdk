package com.appdirect.sdk.appmarket.migration;

import org.springframework.context.annotation.Bean;

import com.appdirect.sdk.appmarket.events.APIResult;

public class DefaultMigrationHandlers {
	@Bean
	public CustomerAccountValidator customerAccountValidator() {
		return (customerAccountData) -> APIResult.success("");
	}
}
