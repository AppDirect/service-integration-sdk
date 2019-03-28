package com.appdirect.sdk.vendorFields.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.vendorFields.controller.VendorRequiredFieldController;
import com.appdirect.sdk.vendorFields.handler.VendorRequiredFieldHandler;
import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.OperationType;

@Configuration
public class VendorRequiredFieldConfiguration {

	@Bean
	public VendorRequiredFieldController vendorRequiredFieldController(VendorRequiredFieldHandler vendorRequiredFieldHandler) {
		return new VendorRequiredFieldController(vendorRequiredFieldHandler);
	}

	@Bean
	public VendorRequiredFieldHandler vendorRequiredFieldHandler() {
		return (String editionCode, FlowType flowType, OperationType operationType, Locale locale) -> {
			throw new UnsupportedOperationException(String.format(
					"Vendor required field Service for editionCode=%s, flow type=%s, operation type=%s and locale=%s is not supported.",
					editionCode,
					flowType,
					operationType,
					locale));
		};
	}
}
