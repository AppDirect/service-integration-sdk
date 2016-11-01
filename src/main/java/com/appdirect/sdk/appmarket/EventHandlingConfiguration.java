package com.appdirect.sdk.appmarket;

import static com.appdirect.sdk.appmarket.api.EventType.SUBSCRIPTION_CANCEL;
import static com.appdirect.sdk.appmarket.api.EventType.SUBSCRIPTION_ORDER;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.appmarket.alt.DeveloperEventHandler;
import com.appdirect.sdk.appmarket.alt.SDKEventHandler;
import com.appdirect.sdk.appmarket.api.EventType;
import com.appdirect.sdk.appmarket.api.SubscriptionCancel;
import com.appdirect.sdk.appmarket.api.SubscriptionOrder;

@Configuration
public class EventHandlingConfiguration {
	private final DeveloperEventHandler<SubscriptionOrder> subscriptionOrderDevHandler;
	private final DeveloperEventHandler<SubscriptionCancel> subscriptionCancelDevHandler;

	@Autowired
	public EventHandlingConfiguration(DeveloperEventHandler<SubscriptionOrder> subscriptionOrderDevHandler, DeveloperEventHandler<SubscriptionCancel> subscriptionCancelDevHandler) {
		this.subscriptionOrderDevHandler = subscriptionOrderDevHandler;
		this.subscriptionCancelDevHandler = subscriptionCancelDevHandler;
	}

	@Bean
	public SDKEventHandler<SubscriptionOrder> subscriptionOrderSdkHandler() {
		return new DeveloperEventHandlerWrapper<>(subscriptionOrderParser(), subscriptionOrderDevHandler);
	}

	@Bean
	public EventParser<SubscriptionOrder> subscriptionOrderParser() {
		return e -> new SubscriptionOrder(); // TODO: add actual parser here
	}

	@Bean
	public EventParser<SubscriptionCancel> subscriptionCancelParser() {
		return e -> new SubscriptionCancel(); // TODO: add actual parser here
	}

	@Bean
	public SDKEventHandler<SubscriptionCancel> subscriptionCancelSdkHandler() {
		return new DeveloperEventHandlerWrapper<>(subscriptionCancelParser(), subscriptionCancelDevHandler);
	}

	@Bean
	public AppmarketEventDispatcher appmarketEventDispatcher() {
		return new AppmarketEventDispatcher(allProcessors());
	}

	private Map<EventType, SDKEventHandler<?>> allProcessors() {
		Map<EventType, SDKEventHandler<?>> allProcessors = new HashMap<>();
		allProcessors.put(SUBSCRIPTION_ORDER, subscriptionOrderSdkHandler());
		allProcessors.put(SUBSCRIPTION_CANCEL, subscriptionCancelSdkHandler());

		return allProcessors;
	}
}
