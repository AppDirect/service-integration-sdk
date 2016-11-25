package com.appdirect.sdk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.web.HealthController;
import com.appdirect.sdk.web.RestOperationsFactory;
import com.appdirect.sdk.web.config.JacksonConfiguration;
import com.appdirect.sdk.web.config.SecurityConfiguration;
import com.appdirect.sdk.web.exception.AppmarketEventConsumerExceptionHandler;
import com.appdirect.sdk.web.oauth.OAuthKeyExtractor;

@Configuration
@Import({JacksonConfiguration.class, SecurityConfiguration.class, EventHandlingConfiguration.class})
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

	@Bean
	public AppmarketEventFetcher appmarketEventFetcher() {
		return new AppmarketEventFetcher(restOperationsFactory());
	}

	@Bean
	public AppmarketEventService appmarketEventService(DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier, AppmarketEventDispatcher eventDispatcher) {
		return new AppmarketEventService(appmarketEventFetcher(), credentialsSupplier, eventDispatcher);
	}

	@Bean
	public AppmarketEventController appmarketEventController(AppmarketEventService appmarketEventService, OAuthKeyExtractor oauthKeyExtractor) {
		return new AppmarketEventController(appmarketEventService, oauthKeyExtractor);
	}
}
