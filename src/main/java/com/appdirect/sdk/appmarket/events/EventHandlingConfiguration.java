package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.ErrorCode.CONFIGURATION_ERROR;
import static java.lang.String.format;
import static java.util.concurrent.Executors.newWorkStealingPool;

import java.util.concurrent.ExecutorService;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.appmarket.AppmarketEventHandler;

@Configuration
@RequiredArgsConstructor
public class EventHandlingConfiguration {
	private final AppmarketEventHandler<SubscriptionOrder> subscriptionOrderHandler;
	private final AppmarketEventHandler<SubscriptionCancel> subscriptionCancelHandler;
	private final AppmarketEventHandler<SubscriptionChange> subscriptionChangeHandler;
	private final AppmarketEventHandler<SubscriptionClosed> subscriptionClosedHandler;
	private final AppmarketEventHandler<SubscriptionDeactivated> subscriptionDeactivatedHandler;
	private final AppmarketEventHandler<SubscriptionReactivated> subscriptionReactivatedHandler;
	private final AppmarketEventHandler<SubscriptionUpcomingInvoice> subscriptionUpcomingInvoiceHandler;
	private final AppmarketEventHandler<AddonSubscriptionOrder> addonSubscriptionOrderHandler;
	private final AppmarketEventHandler<AddonSubscriptionCancel> addonSubscriptionCancelHandler;
	private final AppmarketEventHandler<UserAssignment> userAssignmentHandler;
	private final AppmarketEventHandler<UserUnassignment> userUnassignmentHandler;

	@Bean
	public SDKEventHandler unknownEventHandler() {
		return (event, eventContext) -> new APIResult(CONFIGURATION_ERROR, format("Unsupported event type %s", event.getType()));
	}

	@Bean(destroyMethod = "shutdown")
	public ExecutorService defaultExecutorService() {
		return newWorkStealingPool();
	}

	@Bean
	public AppmarketEventDispatcher appmarketEventDispatcher(AppmarketEventClient appmarketEventClient, 
															 EditionCodeBasedAddonDetector addonDetector) {
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
				new ParseAndHandleWrapper<>(new AddonSubscriptionCancelEventParser(), addonSubscriptionCancelHandler), 
				new ParseAndHandleWrapper<>(new UserAssignmentParser(), userAssignmentHandler),
				new ParseAndHandleWrapper<>(new UserUnassignmentParser(), userUnassignmentHandler),
				unknownEventHandler(),
				addonDetector
		);
	}
}
