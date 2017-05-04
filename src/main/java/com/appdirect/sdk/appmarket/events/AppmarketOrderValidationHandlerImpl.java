package com.appdirect.sdk.appmarket.events;

import java.util.HashSet;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppmarketOrderValidationHandlerImpl implements AppmarketOrderValidationHandler {

	@Override
	public ValidationResponse validateOrderFields(String locale,
												  Map<String, String> orderFields) {
		//Default implementation returns no errors
		return new ValidationResponse(new HashSet<>());
	}

}
