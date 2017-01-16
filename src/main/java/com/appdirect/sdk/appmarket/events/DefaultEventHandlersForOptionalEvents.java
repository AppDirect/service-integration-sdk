package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.APIResult.success;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.appdirect.sdk.appmarket.AppmarketEventHandler;

/**
 * Configuration class supplying event handlers for optional events.
 * Connector developers simply need to annotate handler beans they supply with {@link Primary}
 * and Spring will pick them up instead of defaulting to the "dummy" ones.
 */
public class DefaultEventHandlersForOptionalEvents {
	private final static String DEFAULT_SUCCESS = "This connector does nothing with this event.";

	@Bean
	public AppmarketEventHandler<SubscriptionChange> subscriptionChangeHandler() {
		return (e) -> success(DEFAULT_SUCCESS);
	}

	@Bean
	public AppmarketEventHandler<SubscriptionClosed> subscriptionClosedHandler() {
		return (e) -> success(DEFAULT_SUCCESS);
	}

	@Bean
	public AppmarketEventHandler<SubscriptionDeactivated> subscriptionDeactivatedHandler() {
		return (e) -> success(DEFAULT_SUCCESS);
	}

	@Bean
	public AppmarketEventHandler<SubscriptionReactivated> subscriptionReactivatedHandler() {
		return (e) -> success(DEFAULT_SUCCESS);
	}

	@Bean
	public AppmarketEventHandler<SubscriptionUpcomingInvoice> subscriptionUpcomingInvoiceHandler() {
		return (e) -> success(DEFAULT_SUCCESS);
	}

	@Bean
	public AppmarketEventHandler<AddonSubscriptionOrder> addonSubscriptionOrderHandler() {
		return (e) -> success(DEFAULT_SUCCESS);
	}

	@Bean
	public AppmarketEventHandler<AddonSubscriptionCancel> addonSubscriptionCancelHandler() {
		return (e) -> success(DEFAULT_SUCCESS);
	}

	@Bean
	public AppmarketEventHandler<UserAssignment> userAssignmentHandler() {
		return (e) -> success(DEFAULT_SUCCESS);
	}

	@Bean
	public AppmarketEventHandler<UserUnassignment> userUnassignmentHandler() {
		return (e) -> success(DEFAULT_SUCCESS);
	}
}
