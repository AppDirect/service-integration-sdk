package com.appdirect.sdk.vendorrequiredfields;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class VendorRequiredFieldConfiguration {

	@Bean
	public VendorRequiredFieldController vendorRequiredFieldController(VendorRequiredFieldHandler vendorRequiredFieldHandler) {
		return new VendorRequiredFieldController(vendorRequiredFieldHandler);
	}

	@Bean
	public VendorRequiredFieldHandler vendorRequiredFieldHandler() {
		return () -> {
			throw new UnsupportedOperationException(String.format("Vendor required field Service for partner %s is not supported."));
		};
	}
}
