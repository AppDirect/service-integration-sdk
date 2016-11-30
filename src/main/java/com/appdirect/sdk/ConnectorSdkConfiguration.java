package com.appdirect.sdk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.appdirect.sdk.appmarket.events.AppmarketCommunicationConfiguration;
import com.appdirect.sdk.appmarket.events.DeveloperExceptionHandler;
import com.appdirect.sdk.appmarket.events.EventHandlingConfiguration;
import com.appdirect.sdk.web.RestOperationsFactory;
import com.appdirect.sdk.web.config.JacksonConfiguration;
import com.appdirect.sdk.web.oauth.SecurityConfiguration;
import com.appdirect.sdk.web.exception.AppmarketEventConsumerExceptionHandler;

@Configuration
@Import({
	JacksonConfiguration.class,
	SecurityConfiguration.class,
	EventHandlingConfiguration.class,
	AppmarketCommunicationConfiguration.class
})
public class ConnectorSdkConfiguration {

	@Bean
	public AppmarketEventConsumerExceptionHandler appmarketEventConsumerExceptionHandler() {
		return new AppmarketEventConsumerExceptionHandler();
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
