package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.ErrorCode.CONFIGURATION_ERROR;
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
	private AppmarketEventHandler<AddonSubscriptionOrder> addonSubscriptionOrderHandler;
	@Autowired
	private AppmarketEventHandler<UserAssignment> userAssignmentHandler;
	@Autowired
	private AppmarketEventHandler<UserUnassignment> userUnassignmentHandler;

	@Bean
	public SDKEventHandler unknownEventHandler() {
		return (event, eventContext) -> new APIResult(CONFIGURATION_ERROR, format("Unsupported event type %s", event.getType()));
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

	@Bean(destroyMethod = "shutdown")
	public ExecutorService defaultExecutorService() {
		return newWorkStealingPool();
	}

	@Bean
	public AppmarketEventDispatcher appmarketEventDispatcher(AppmarketEventClient appmarketEventClient, EditionCodeBasedAddonDetector addonDetector) {
		return new AppmarketEventDispatcher(
				new Events(),
				new AsyncEventHandler(defaultExecutorService(), appmarketEventClient),
				new ParseAndHandleWrapper<>(new SubscriptionOrderEventParser(), subscriptionOrderHandler),
				new ParseAndHandleWrapper<>(new SubscriptionCancelEventParser(), subscriptionCancelHandler),
				new ParseAndHandleWrapper<>(new SubscriptionChangeEventParser(), subscriptionChangeHandler),
				new ParseAndHandleWrapper<>(new SubscriptionDeactivatedParser(), subscriptionDeactivatedHandler),
				new ParseAndHandleWrapper<>(new SubscriptionReactivatedParser(), subscriptionReactivatedHandler),
				new ParseAndHandleWrapper<>(new SubscriptionClosedParser(), subscriptionClosedHandler),
				new ParseAndHandleWrapper<>(new SubscriptionUpcomingInvoiceParser(), subscriptionUpcomingInvoiceHandler),
				new ParseAndHandleWrapper<>(new AddonSubscriptionOrderEventParser(), addonSubscriptionOrderHandler),
				unknownEventHandler(),
				userAssignmentHandler(),
				userUnassignmentHandler(),
				addonDetector
		);
	}
}
