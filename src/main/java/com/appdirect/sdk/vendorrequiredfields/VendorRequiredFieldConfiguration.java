package com.appdirect.sdk.vendorrequiredfields;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.vendorrequiredfields.model.FlowType;


@Configuration
public class VendorRequiredFieldConfiguration {

	@Bean
	public VendorRequiredFieldController vendorRequiredFieldController(VendorRequiredFieldHandler vendorRequiredFieldHandler) {
		return new VendorRequiredFieldController(vendorRequiredFieldHandler);
	}

	@Bean
	public VendorRequiredFieldHandler vendorRequiredFieldHandler() {
		return (String sku, FlowType flowType) -> {
			throw new UnsupportedOperationException(String.format("Vendor required field Service for sku=%s and flow type=%s is not supported.", sku, flowType));
		};
	}
}
