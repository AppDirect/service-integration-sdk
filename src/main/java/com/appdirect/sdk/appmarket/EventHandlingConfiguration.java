package com.appdirect.sdk.appmarket;

import static com.appdirect.sdk.appmarket.api.EventType.SUBSCRIPTION_CANCEL;
import static com.appdirect.sdk.appmarket.api.EventType.SUBSCRIPTION_ORDER;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.appmarket.api.EventType;
import com.appdirect.sdk.appmarket.api.SubscriptionCancel;
import com.appdirect.sdk.appmarket.api.SubscriptionOrder;

@Configuration
public class EventHandlingConfiguration {
	private final AppmarketEventHandler<SubscriptionOrder> subscriptionOrderHandler;
	private final AppmarketEventHandler<SubscriptionCancel> subscriptionCancelHandler;

	@Autowired
	public EventHandlingConfiguration(AppmarketEventHandler<SubscriptionOrder> subscriptionOrderHandler, AppmarketEventHandler<SubscriptionCancel> subscriptionCancelHandler) {
		this.subscriptionOrderHandler = subscriptionOrderHandler;
		this.subscriptionCancelHandler = subscriptionCancelHandler;
	}

	@Bean
	public SDKEventHandler subscriptionOrderSdkHandler() {
		return new ParseAndHandleWrapper<>(subscriptionOrderParser(), subscriptionOrderHandler);
	}

	@Bean
	public EventParser<SubscriptionOrder> subscriptionOrderParser() {
		return e -> new SubscriptionOrder();
	}

	@Bean
	public EventParser<SubscriptionCancel> subscriptionCancelParser() {
		return e -> new SubscriptionCancel();
	}

	@Bean
	public SDKEventHandler subscriptionCancelSdkHandler() {
		return new ParseAndHandleWrapper<>(subscriptionCancelParser(), subscriptionCancelHandler);
	}

	@Bean
	public AppmarketEventDispatcher appmarketEventDispatcher() {
		return new AppmarketEventDispatcher(allHandlers());
	}

	@Bean
	public Map<EventType, SDKEventHandler> allHandlers() {
		Map<EventType, SDKEventHandler> allProcessors = new EnumMap<>(EventType.class);
		allProcessors.put(SUBSCRIPTION_ORDER, subscriptionOrderSdkHandler());
		allProcessors.put(SUBSCRIPTION_CANCEL, subscriptionCancelSdkHandler());

		return Collections.unmodifiableMap(allProcessors);
	}
}
