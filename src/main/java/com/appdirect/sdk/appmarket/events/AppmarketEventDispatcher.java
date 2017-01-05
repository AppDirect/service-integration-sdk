package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_CANCEL;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_CHANGE;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_NOTICE;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_ORDER;
import static com.appdirect.sdk.appmarket.events.EventType.USER_ASSIGNMENT;
import static com.appdirect.sdk.appmarket.events.EventType.USER_UNASSIGNMENT;
import static com.appdirect.sdk.appmarket.events.NoticeType.CLOSED;
import static com.appdirect.sdk.appmarket.events.NoticeType.DEACTIVATED;
import static com.appdirect.sdk.appmarket.events.NoticeType.REACTIVATED;
import static com.appdirect.sdk.appmarket.events.NoticeType.UPCOMING_INVOICE;
import static java.util.Collections.unmodifiableMap;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

class AppmarketEventDispatcher {
	private final Events events;
	private final AsyncEventHandler asyncHandler;
	private final Map<NoticeType, SDKEventHandler> noticeEventsToHandlers;
	private final SDKEventHandler subscriptionOrderHandler;
	private final SDKEventHandler subscriptionCancelHandler;
	private final SDKEventHandler subscriptionChangeHandler;
	private final SDKEventHandler addonSubscriptionOrderHandler;
	private final SDKEventHandler addonSubscriptionCancelHandler;
	private final SDKEventHandler userAssignmentHandler;
	private final SDKEventHandler userUnassignmentHandler;
	private final SDKEventHandler unknownEventHandler;
	private final EditionCodeBasedAddonDetector addonDetector;

	AppmarketEventDispatcher(Events events,
							 AsyncEventHandler asyncHandler,
							 SDKEventHandler subscriptionOrderHandler,
							 SDKEventHandler subscriptionCancelHandler,
							 SDKEventHandler subscriptionChangeHandler,
							 SDKEventHandler subscriptionDeactivatedHandler,
							 SDKEventHandler subscriptionReactivatedHandler,
							 SDKEventHandler subscriptionClosedHandler,
							 SDKEventHandler subscriptionUpcomingInvoiceHandler,
							 SDKEventHandler addonSubscriptionOrderHandler,
							 SDKEventHandler addonSubscriptionCancelHandler, 
							 SDKEventHandler userAssignmentHandler,
							 SDKEventHandler userUnassignmentHandler,
							 SDKEventHandler unknownEventHandler,
							 EditionCodeBasedAddonDetector addonDetector) { // NOSONAR: ctor has too many params - This is for SDK use only.
		this.events = events;
		this.asyncHandler = asyncHandler;
		this.subscriptionOrderHandler = subscriptionOrderHandler;
		this.subscriptionCancelHandler = subscriptionCancelHandler;
		this.subscriptionChangeHandler = subscriptionChangeHandler;
		this.addonSubscriptionOrderHandler = addonSubscriptionOrderHandler;
		this.addonSubscriptionCancelHandler = addonSubscriptionCancelHandler;
		this.noticeEventsToHandlers = buildNoticeHandlersMap(subscriptionDeactivatedHandler, subscriptionReactivatedHandler, subscriptionClosedHandler, subscriptionUpcomingInvoiceHandler);
		this.userAssignmentHandler = userAssignmentHandler;
		this.userUnassignmentHandler = userUnassignmentHandler;
		this.unknownEventHandler = unknownEventHandler;
		this.addonDetector = addonDetector;
	}

	APIResult dispatchAndHandle(EventInfo rawEvent, EventHandlingContext eventContext) {
		SDKEventHandler eventHandler = getHandlerFor(rawEvent);
		if (events.eventShouldBeHandledAsync(rawEvent)) {
			return asyncHandler.handle(eventHandler, rawEvent, eventContext);
		} else {
			return eventHandler.handle(rawEvent, eventContext);
		}
	}

	private SDKEventHandler getHandlerFor(final EventInfo rawEvent) {
		final boolean eventIsForAddon = events.extractEditionCode(rawEvent)
				.map(addonDetector::editionCodeIsRelatedToAddon)
				.orElse(false);

		Map<EventType, Supplier<SDKEventHandler>> eventsToHandlers = new EnumMap<>(EventType.class);
		eventsToHandlers.put(SUBSCRIPTION_ORDER, () -> eventIsForAddon ? addonSubscriptionOrderHandler : subscriptionOrderHandler);
		eventsToHandlers.put(SUBSCRIPTION_CANCEL, () -> eventIsForAddon ? addonSubscriptionCancelHandler : subscriptionCancelHandler);
		eventsToHandlers.put(SUBSCRIPTION_CHANGE, () -> subscriptionChangeHandler);
		eventsToHandlers.put(SUBSCRIPTION_NOTICE, () -> subscriptionNoticeHandlerFor(rawEvent.getPayload().getNotice().getType()));
		eventsToHandlers.put(USER_ASSIGNMENT, () -> userAssignmentHandler);
		eventsToHandlers.put(USER_UNASSIGNMENT, () -> userUnassignmentHandler);

		return eventsToHandlers.getOrDefault(rawEvent.getType(), () -> unknownEventHandler).get();
	}

	private SDKEventHandler subscriptionNoticeHandlerFor(NoticeType noticeType) {
		return noticeEventsToHandlers.getOrDefault(noticeType, unknownEventHandler);
	}

	private Map<NoticeType, SDKEventHandler> buildNoticeHandlersMap(SDKEventHandler subscriptionDeactivatedHandler, SDKEventHandler subscriptionReactivatedHandler, SDKEventHandler subscriptionClosedHandler, SDKEventHandler subscriptionUpcomingInvoiceHandler) {
		Map<NoticeType, SDKEventHandler> eventsToHandlers = new EnumMap<>(NoticeType.class);
		eventsToHandlers.put(CLOSED, subscriptionClosedHandler);
		eventsToHandlers.put(DEACTIVATED, subscriptionDeactivatedHandler);
		eventsToHandlers.put(REACTIVATED, subscriptionReactivatedHandler);
		eventsToHandlers.put(UPCOMING_INVOICE, subscriptionUpcomingInvoiceHandler);
		return unmodifiableMap(eventsToHandlers);
	}
}
