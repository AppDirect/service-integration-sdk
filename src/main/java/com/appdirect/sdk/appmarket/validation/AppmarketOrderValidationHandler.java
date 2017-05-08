package com.appdirect.sdk.appmarket.validation;

import java.util.Map;

public interface AppmarketOrderValidationHandler {
	ValidationResponse validateOrderFields(Map<String, String> orderFields);
}
