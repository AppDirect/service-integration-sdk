package com.appdirect.sdk;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.appdirect.sdk.appmarket.AppmarketEventProcessor;
import com.appdirect.sdk.appmarket.AppmarketEventProcessorRegistry;
import com.appdirect.sdk.appmarket.IsvSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.web.AppmarketEventController;
import com.appdirect.sdk.web.AppmarketEventFetcher;
import com.appdirect.sdk.web.AppmarketEventService;
import com.appdirect.sdk.web.config.JacksonConfiguration;
import com.appdirect.sdk.web.config.SecurityConfiguration;
import com.appdirect.sdk.web.exception.AppmarketEventConsumerExceptionHandler;

@Configuration
@Import({JacksonConfiguration.class, SecurityConfiguration.class})
public class ConnectorSdkConfiguration {

	@Bean
	public AppmarketEventConsumerExceptionHandler appmarketEventConsumerExceptionHandler() {
		return new AppmarketEventConsumerExceptionHandler();
	}

	@Bean
	public AppmarketEventProcessorRegistry appmarketEventProcessorRegistry(Set<AppmarketEventProcessor> processors) {
		return new AppmarketEventProcessorRegistry(processors);
	}

	@Bean
	public AppmarketEventFetcher appmarketEventFetcher() {
		return new AppmarketEventFetcher(appmarketEventConsumerExceptionHandler());
	}

	@Bean
	public AppmarketEventService appmarketEventService(AppmarketEventProcessorRegistry appmarketEventProcessorRegistry,
													   IsvSpecificAppmarketCredentialsSupplier credentialsSupplier) {
		return new AppmarketEventService(appmarketEventFetcher(), appmarketEventProcessorRegistry, credentialsSupplier);
	}

	@Bean
	public AppmarketEventController appmarketEventController(AppmarketEventService appmarketEventService) {
		return new AppmarketEventController(appmarketEventService);
	}
}
