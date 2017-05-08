package com.appdirect.sdk.appmarket.validation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultValidationHandlers {

	@Bean
	public AppmarketOrderValidationController validationEndpointController(AppmarketOrderValidationHandler validationHandler) {
		return new AppmarketOrderValidationController(validationHandler);
	}

	@Bean
	public AppmarketOrderValidationHandler orderFieldsValidationHandler() {
		return new AppmarketOrderValidationHandlerImpl();
	}
}
