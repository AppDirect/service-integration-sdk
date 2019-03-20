package com.appdirect.sdk.vendorFields.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.vendorFields.controller.VendorFieldValidationController;
import com.appdirect.sdk.vendorFields.handler.VendorFieldValidationHandler;
import com.appdirect.sdk.vendorFields.model.VendorFieldsValidationRequest;

@Configuration
@ComponentScan(basePackages = {"com.appdirect.sdk.vendorFields.controller"})
public class VendorFieldConfiguration {

	@Bean
	public VendorFieldValidationController vendorRequiredFieldController(VendorFieldValidationHandler vendorFieldValidationHandler) {
		return new VendorFieldValidationController(vendorFieldValidationHandler);
	}

	@Bean
	public VendorFieldValidationHandler vendorFieldValidationHandler() {
		return (VendorFieldsValidationRequest vendorFieldsValidationRequest) -> {
			throw new UnsupportedOperationException(String.format(
					"Vendor Fields Validation Service for sku=%s, flowType=%s and operationType=%s is not supported.",
					vendorFieldsValidationRequest.getSku(),
					vendorFieldsValidationRequest.getFlowType(),
					vendorFieldsValidationRequest.getOperationType()));
		};
	}
}
