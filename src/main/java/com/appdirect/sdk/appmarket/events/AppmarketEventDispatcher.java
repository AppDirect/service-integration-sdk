package com.appdirect.sdk.appmarket.events;

import java.util.function.Predicate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AppmarketEventDispatcher {
	private final Events events;
	private final AsyncEventHandler asyncHandler;
	private final SDKEventHandler subscriptionOrderHandler;
	private final SDKEventHandler subscriptionCancelHandler;
	private final SDKEventHandler subscriptionChangeHandler;
	private final SDKEventHandler subscriptionDeactivatedHandler;
	private final SDKEventHandler subscriptionReactivatedHandler;
	private final SDKEventHandler subscriptionClosedHandler;
	private final SDKEventHandler subscriptionUpcomingInvoiceHandler;
	private final SDKEventHandler addonSubscriptionOrderHandler;
	private final SDKEventHandler unknownEventHandler;
	private final SDKEventHandler userAssignmentHandler;
	private final SDKEventHandler userUnassignmentHandler;
	private final Predicate<EventInfo> addonDetector;

	APIResult dispatchAndHandle(EventInfo rawEvent, EventHandlingContext eventContext) {
		SDKEventHandler eventHandler = getHandlerFor(eventInfo);
		if (events.eventShouldBeHandledAsync(eventInfo)) {
			return asyncHandler.handle(eventHandler, eventInfo, eventContext);
		} else {
			return eventHandler.handle(rawEvent, eventContext);
		}
	}

	private SDKEventHandler getHandlerFor(EventInfo rawEvent) {
		boolean eventIsForAddon = addonDetector.test(rawEvent);

		switch (rawEvent.getType()) {
			case SUBSCRIPTION_ORDER:
				return eventIsForAddon ? addonSubscriptionOrderHandler : subscriptionOrderHandler;
			case SUBSCRIPTION_CANCEL:
				return subscriptionCancelHandler;
			case SUBSCRIPTION_CHANGE:
				return subscriptionChangeHandler;
			case SUBSCRIPTION_NOTICE:
				return subscriptionNoticeHandlerFor(rawEvent.getPayload().getNotice().getType());
			case USER_ASSIGNMENT:
				return userAssignmentHandler;
			case USER_UNASSIGNMENT:
				return userUnassignmentHandler;
			default:
				return unknownEventHandler;
		}
	}

	private SDKEventHandler subscriptionNoticeHandlerFor(NoticeType noticeType) {
		switch (noticeType) {
			case CLOSED:
				return subscriptionClosedHandler;
			case DEACTIVATED:
				return subscriptionDeactivatedHandler;
			case REACTIVATED:
				return subscriptionReactivatedHandler;
			case UPCOMING_INVOICE:
				return subscriptionUpcomingInvoiceHandler;
			default:
				return unknownEventHandler;
		}
	}
}
