package com.appdirect.sdk.appmarket.alt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.appmarket.AppmarketEventDispatcher;
import com.appdirect.sdk.appmarket.api.SubscriptionOrder;

@Configuration
public class EventHandlingConfiguration {

	@Bean
	public EventParser<SubscriptionOrder> subscriptionOrderParser() {
		return e -> new SubscriptionOrder();
	}

	@Bean
	public AppmarketEventDispatcher appmarketEventDispatcher() {
		return new AppmarketEventDispatcher();
	}

	@Bean
	public DeveloperEventHandlerWrapper<SubscriptionOrder> subscriptionOrderSdkHandler(DeveloperEventHandler<SubscriptionOrder> devHandler) {
		return new DeveloperEventHandlerWrapper<>(subscriptionOrderParser(), devHandler);
	}
}
