package com.appdirect.sdk.appmarket.migration;

import java.util.Map;

import com.appdirect.sdk.appmarket.events.APIResult;

@FunctionalInterface
public interface CustomerAccountValidationHandler {
	APIResult validate(Map<String, String> customerAccountData);
}
