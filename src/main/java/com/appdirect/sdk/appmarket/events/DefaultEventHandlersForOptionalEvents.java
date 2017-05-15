/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.APIResult.failure;
import static com.appdirect.sdk.appmarket.events.ErrorCode.CONFIGURATION_ERROR;
import static java.lang.String.format;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.appdirect.sdk.appmarket.AppmarketEventHandler;

/**
 * Configuration class supplying event handlers for optional events.
 * Connector developers simply need to annotate handler beans they supply with {@link Primary}
 * and Spring will pick them up instead of defaulting to the "dummy" ones.
 */
public class DefaultEventHandlersForOptionalEvents {

	@Bean
	public AppmarketEventHandler<SubscriptionChange> subscriptionChangeHandler() {
		return e -> defaultErrorResponse("SUBSCRIPTION_CHANGE");
	}

	@Bean
	public AppmarketEventHandler<SubscriptionClosed> subscriptionClosedHandler() {
		return e -> defaultErrorResponse("SUBSCRIPTION_NOTICE of type CLOSED");
	}

	@Bean
	public AppmarketEventHandler<SubscriptionDeactivated> subscriptionDeactivatedHandler() {
		return e -> defaultErrorResponse("SUBSCRIPTION_NOTICE of type DEACTIVATED");
	}

	@Bean
	public AppmarketEventHandler<SubscriptionReactivated> subscriptionReactivatedHandler() {
		return e -> defaultErrorResponse("SUBSCRIPTION_NOTICE of type REACTIVATED");
	}

	@Bean
	public AppmarketEventHandler<SubscriptionUpcomingInvoice> subscriptionUpcomingInvoiceHandler() {
		return e -> defaultErrorResponse("SUBSCRIPTION_NOTICE of type UPCOMING_INVOICE");
	}

	@Bean
	public AppmarketEventHandler<AddonSubscriptionOrder> addonSubscriptionOrderHandler() {
		return e -> defaultErrorResponse("SUBSCRIPTION_ORDER (for add-ons v2)");
	}

	@Bean
	public AppmarketEventHandler<AddonSubscriptionCancel> addonSubscriptionCancelHandler() {
		return e -> defaultErrorResponse("SUBSCRIPTION_CANCEL (for add-ons v2)");
	}

	@Bean
	public AppmarketEventHandler<UserAssignment> userAssignmentHandler() {
		return e -> defaultErrorResponse("USER_ASSIGNMENT");
	}

	@Bean
	public AppmarketEventHandler<UserUnassignment> userUnassignmentHandler() {
		return e -> defaultErrorResponse("USER_UNASSIGNMENT");
	}

	private APIResult defaultErrorResponse(String eventType) {
		return failure(CONFIGURATION_ERROR, format("This event type (%s) is not supported by this connector.", eventType));
	}
}
