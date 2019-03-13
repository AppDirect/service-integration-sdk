package com.appdirect.sdk.vendorFields.fieldsValidation;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.OperationType;

@Configuration
public class VendorFieldConfiguration {
	@Bean
	public VendorFieldValidationHandler vendorFieldValidationHandler() {
		return (String sku, FlowType flowType, OperationType operationType, Map<String, String> fieldValues) -> {
			throw new UnsupportedOperationException(String.format("Vendor Fields Validation Service for sku=%s, flowType=%s and operationType=%s is not supported.", sku, flowType, operationType));
		};
	}
}
