package com.appdirect.sdk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.appdirect.sdk.appmarket.api.AppmarketCommunicationConfiguration;
import com.appdirect.sdk.appmarket.api.EventHandlingConfiguration;
import com.appdirect.sdk.web.HealthController;
import com.appdirect.sdk.web.RestOperationsFactory;
import com.appdirect.sdk.web.config.JacksonConfiguration;
import com.appdirect.sdk.web.config.SecurityConfiguration;
import com.appdirect.sdk.web.exception.AppmarketEventConsumerExceptionHandler;

@Configuration
@Import({JacksonConfiguration.class, SecurityConfiguration.class, EventHandlingConfiguration.class, AppmarketCommunicationConfiguration.class})
public class ConnectorSdkConfiguration {

	@Bean
	public HealthController healthController() {
		return new HealthController();
	}

	@Bean
	public AppmarketEventConsumerExceptionHandler appmarketEventConsumerExceptionHandler() {
		return new AppmarketEventConsumerExceptionHandler();
	}

	@Bean
	public RestOperationsFactory restOperationsFactory() {
		return new RestOperationsFactory(appmarketEventConsumerExceptionHandler());
	}

}
