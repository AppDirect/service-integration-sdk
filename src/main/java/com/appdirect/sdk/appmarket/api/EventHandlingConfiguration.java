package com.appdirect.sdk.appmarket.api;

import static java.lang.String.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.appmarket.AppmarketEventHandler;

@Configuration
public class EventHandlingConfiguration {
	private final AppmarketEventHandler<SubscriptionOrder> subscriptionOrderHandler;
	private final AppmarketEventHandler<SubscriptionCancel> subscriptionCancelHandler;
	private final AppmarketEventHandler<SubscriptionChange> subscriptionChangeHandler;
	private final AppmarketEventHandler<SubscriptionClosed> subscriptionClosedHandler;
	private final AppmarketEventHandler<SubscriptionDeactivated> subscriptionDeactivatedHandler;
	private final AppmarketEventHandler<SubscriptionReactivated> subscriptionReactivatedHandler;
	private final AppmarketEventHandler<SubscriptionUpcomingInvoice> subscriptionUpcomingInvoiceHandler;

	@Autowired
	public EventHandlingConfiguration(
		AppmarketEventHandler<SubscriptionOrder> subscriptionOrderHandler,
		AppmarketEventHandler<SubscriptionCancel> subscriptionCancelHandler,
		AppmarketEventHandler<SubscriptionChange> subscriptionChangeHandler,
		AppmarketEventHandler<SubscriptionClosed> subscriptionClosedHandler,
		AppmarketEventHandler<SubscriptionDeactivated> subscriptionDeactivatedHandler,
		AppmarketEventHandler<SubscriptionReactivated> subscriptionReactivatedHandler,
		AppmarketEventHandler<SubscriptionUpcomingInvoice> subscriptionUpcomingInvoiceHandler) {

		this.subscriptionOrderHandler = subscriptionOrderHandler;
		this.subscriptionCancelHandler = subscriptionCancelHandler;
		this.subscriptionChangeHandler = subscriptionChangeHandler;
		this.subscriptionClosedHandler = subscriptionClosedHandler;
		this.subscriptionDeactivatedHandler = subscriptionDeactivatedHandler;
		this.subscriptionReactivatedHandler = subscriptionReactivatedHandler;
		this.subscriptionUpcomingInvoiceHandler = subscriptionUpcomingInvoiceHandler;
	}

	@Bean
	public SDKEventHandler subscriptionOrderSdkHandler() {
		return new ParseAndHandleWrapper<>(subscriptionOrderParser(), subscriptionOrderHandler);
	}

	@Bean
	public SDKEventHandler subscriptionCancelSdkHandler() {
		return new ParseAndHandleWrapper<>(subscriptionCancelParser(), subscriptionCancelHandler);
	}

	@Bean
	public SDKEventHandler subscriptionChangeSdkHandler() {
		return new ParseAndHandleWrapper<>(subscriptionChangeEventParser(), subscriptionChangeHandler);
	}

	@Bean
	public SDKEventHandler subscriptionClosedSdkHandler() {
		return new ParseAndHandleWrapper<>(subscriptionClosedEventParser(), subscriptionClosedHandler);
	}

	@Bean
	public SDKEventHandler subscriptionDeactivatedSdkHandler() {
		return new ParseAndHandleWrapper<>(subscriptionDeactivatedEventParser(), subscriptionDeactivatedHandler);
	}

	@Bean
	public SDKEventHandler subscriptionReactivatedSdkHandler() {
		return new ParseAndHandleWrapper<>(subscriptionReactivatedEventParser(), subscriptionReactivatedHandler);
	}

	@Bean
	public SDKEventHandler subscriptionUpcomingInvoiceSdkHandler() {
		return new ParseAndHandleWrapper<>(subscriptionUpcomingInvoiceEventParser(), subscriptionUpcomingInvoiceHandler);
	}

	@Bean
	public SDKEventHandler unknownEventHandler() {
		return (consumerKeyUsedByTheRequest, event) -> new APIResult(ErrorCode.CONFIGURATION_ERROR, format("Unsupported event type %s", event.getType()));
	}

	@Bean
	public EventParser<SubscriptionClosed> subscriptionClosedEventParser() {
		return new SubscriptionClosedParser();
	}

	@Bean
	public EventParser<SubscriptionDeactivated> subscriptionDeactivatedEventParser() {
		return new SubscriptionDeactivatedParser();
	}

	@Bean
	public EventParser<SubscriptionReactivated> subscriptionReactivatedEventParser() {
		return new SubscriptionReactivatedParser();
	}

	@Bean
	public EventParser<SubscriptionUpcomingInvoice> subscriptionUpcomingInvoiceEventParser() {
		return new SubscriptionUpcomingInvoiceParser();
	}

	@Bean
	public EventParser<SubscriptionOrder> subscriptionOrderParser() {
		return new SubscriptionOrderEventParser();
	}

	@Bean
	public EventParser<SubscriptionCancel> subscriptionCancelParser() {
		return new SubscriptionCancelEventParser();
	}

	@Bean
	public EventParser<SubscriptionChange> subscriptionChangeEventParser() {
		return new SubscriptionChangeEventParser();
	}

	@Bean
	public AppmarketEventDispatcher appmarketEventDispatcher() {
		return new AppmarketEventDispatcher(
			subscriptionOrderSdkHandler(),
			subscriptionCancelSdkHandler(),
			subscriptionChangeSdkHandler(),
			subscriptionDeactivatedSdkHandler(),
			subscriptionReactivatedSdkHandler(),
			subscriptionClosedSdkHandler(),
			subscriptionUpcomingInvoiceSdkHandler(),
			unknownEventHandler()
		);
	}
}
