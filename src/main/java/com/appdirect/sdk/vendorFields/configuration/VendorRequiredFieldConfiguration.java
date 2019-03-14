package com.appdirect.sdk.vendorFields.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.handler.VendorRequiredFieldHandler;

@Configuration
public class VendorRequiredFieldConfiguration {
	@Bean
	public VendorRequiredFieldHandler vendorRequiredFieldHandler() {
		return (String sku, FlowType flowType, OperationType operationType) -> {
			throw new UnsupportedOperationException(String.format("Vendor required field Service for sku=%s, flow type=%s and operation type=%s is not supported.", sku, flowType, operationType));
		};
	}
}
