package com.appdirect.sdk.appmarket.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.web.RestOperationsFactory;
import com.appdirect.sdk.web.oauth.OAuthKeyExtractor;

@Configuration
public class AppmarketCommunicationConfiguration {
	@Bean
	public AppmarketEventFetcher appmarketEventFetcher(RestOperationsFactory restOperationsFactory) {
		return new AppmarketEventFetcher(restOperationsFactory);
	}

	@Bean
	public AppmarketEventService appmarketEventService(
		DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier, 
		AppmarketEventDispatcher eventDispatcher,
		AppmarketEventFetcher appmarketEventFetcher) {
		return new AppmarketEventService(appmarketEventFetcher, credentialsSupplier, eventDispatcher);
	}

	@Bean
	public AppmarketEventController appmarketEventController(AppmarketEventService appmarketEventService, OAuthKeyExtractor oauthKeyExtractor) {
		return new AppmarketEventController(appmarketEventService, oauthKeyExtractor);
	}
}
