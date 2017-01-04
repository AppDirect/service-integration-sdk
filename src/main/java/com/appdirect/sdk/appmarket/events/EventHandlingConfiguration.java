package com.appdirect.sdk.appmarket.events;

import static java.lang.String.format;
import static java.util.concurrent.Executors.newWorkStealingPool;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.appmarket.AppmarketEventHandler;

@Configuration
public class EventHandlingConfiguration {
	@Autowired
	private AppmarketEventHandler<SubscriptionOrder> subscriptionOrderHandler;
	@Autowired
	private AppmarketEventHandler<SubscriptionCancel> subscriptionCancelHandler;
	@Autowired
	private AppmarketEventHandler<SubscriptionChange> subscriptionChangeHandler;
	@Autowired
	private AppmarketEventHandler<SubscriptionClosed> subscriptionClosedHandler;
	@Autowired
	private AppmarketEventHandler<SubscriptionDeactivated> subscriptionDeactivatedHandler;
	@Autowired
	private AppmarketEventHandler<SubscriptionReactivated> subscriptionReactivatedHandler;
	@Autowired
	private AppmarketEventHandler<SubscriptionUpcomingInvoice> subscriptionUpcomingInvoiceHandler;
	@Autowired
	private AppmarketEventHandler<UserAssignment> userAssignmentHandler;
	@Autowired
	private AppmarketEventHandler<UserUnassignment> userUnassignmentHandler;

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
		return (event, eventContext) -> new APIResult(ErrorCode.CONFIGURATION_ERROR, format("Unsupported event type %s", event.getType()));
	}

	@Bean
	public SDKEventHandler userAssignmentHandler() {
		return new ParseAndHandleWrapper<>(userAssignmentEventParser(), userAssignmentHandler);
	}

	@Bean
	public SDKEventHandler userUnassignmentHandler() {
		return new ParseAndHandleWrapper<>(userUnassignmentEventParser(), userUnassignmentHandler);
	}

	@Bean
	public EventParser<UserAssignment> userAssignmentEventParser() {
		return new UserAssignmentParser();
	}

	@Bean
	public EventParser<UserUnassignment> userUnassignmentEventParser() {
		return new UserUnassignmentParser();
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

	@Bean(destroyMethod = "shutdown")
	public ExecutorService defaultExecutorService() {
		return newWorkStealingPool();
	}

	@Bean
	public AppmarketEventDispatcher appmarketEventDispatcher(AppmarketEventClient appmarketEventClient) {
		return new AppmarketEventDispatcher(
			new Events(),
			new AsyncEventHandler(defaultExecutorService(), appmarketEventClient),
			subscriptionOrderSdkHandler(),
			subscriptionCancelSdkHandler(),
			subscriptionChangeSdkHandler(),
			subscriptionDeactivatedSdkHandler(),
			subscriptionReactivatedSdkHandler(),
			subscriptionClosedSdkHandler(),
			subscriptionUpcomingInvoiceSdkHandler(),
			unknownEventHandler(),
			userAssignmentHandler(),
			userUnassignmentHandler()
		);
	}
}
