package com.appdirect.sdk.appmarket.events;

import java.util.Map;

public interface AppmarketOrderValidationHandler {
	ValidationResponse validateOrderFields(String locale, Map<String, String> orderFields);
}
