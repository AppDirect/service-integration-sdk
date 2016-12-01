package com.appdirect.sdk.appmarket.events;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.web.RestOperationsFactory;
import com.appdirect.sdk.web.oauth.OAuthKeyExtractor;

@Configuration
public class AppmarketCommunicationConfiguration {
	@Bean
	public AppmarketEventClient appmarketEventFetcher(RestOperationsFactory restOperationsFactory, DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier) {
		return new AppmarketEventClient(restOperationsFactory, credentialsSupplier);
	}

	@Bean
	public AppmarketEventService appmarketEventService(
		DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier,
		AppmarketEventDispatcher eventDispatcher,
		AppmarketEventClient appmarketEventClient) {
		return new AppmarketEventService(appmarketEventClient, credentialsSupplier, eventDispatcher);
	}

	@Bean
	public AppmarketEventController appmarketEventController(AppmarketEventService appmarketEventService, OAuthKeyExtractor oauthKeyExtractor) {
		return new AppmarketEventController(appmarketEventService, oauthKeyExtractor);
	}
}
