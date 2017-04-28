package com.appdirect.sdk.appmarket.validation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.appmarket.events.AppmarketOrderValidationHandler;
import com.appdirect.sdk.appmarket.events.AppmarketOrderValidationHandlerImpl;

@Configuration
public class DefaultValidationHandlers {

	@Bean
	public AppmarketOrderValidationHandler orderFieldsValidationHandler() {
		return new AppmarketOrderValidationHandlerImpl();
	}
}
