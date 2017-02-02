package com.appdirect.sdk.appmarket.migration;

import com.appdirect.sdk.appmarket.events.APIResult;

@FunctionalInterface
public interface CustomerAccountValidationHandler {
	APIResult validate(CustomerAccount customerAccountData);
}
