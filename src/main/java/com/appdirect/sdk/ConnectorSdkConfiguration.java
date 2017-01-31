package com.appdirect.sdk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.appdirect.sdk.appmarket.events.AppmarketCommunicationConfiguration;
import com.appdirect.sdk.appmarket.events.DefaultEventHandlersForOptionalEvents;
import com.appdirect.sdk.appmarket.events.DeveloperExceptionHandler;
import com.appdirect.sdk.appmarket.events.EventHandlingConfiguration;
import com.appdirect.sdk.appmarket.migration.DefaultMigrationHandlers;
import com.appdirect.sdk.web.RestOperationsFactory;
import com.appdirect.sdk.web.config.JacksonConfiguration;
import com.appdirect.sdk.web.exception.AppmarketEventClientExceptionHandler;
import com.appdirect.sdk.web.oauth.SecurityConfiguration;

@Configuration
@Import({
	JacksonConfiguration.class,
	SecurityConfiguration.class,
	DefaultEventHandlersForOptionalEvents.class,
	EventHandlingConfiguration.class,
	AppmarketCommunicationConfiguration.class,
	DefaultMigrationHandlers.class
})
public class ConnectorSdkConfiguration {

	@Bean
	public AppmarketEventClientExceptionHandler appmarketEventConsumerExceptionHandler() {
		return new AppmarketEventClientExceptionHandler();
	}

	@Bean
	public RestOperationsFactory restOperationsFactory() {
		return new RestOperationsFactory(appmarketEventConsumerExceptionHandler());
	}

	@Bean
	public DeveloperExceptionHandler developerExceptionHandler() {
		return new DeveloperExceptionHandler();
	}

}
