package com.appdirect.sdk.appmarket.events;

import java.util.Map;

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
	private final SDKEventHandler unknownEventHandler;

	APIResult dispatchAndHandle(String consumerKeyUsedByTheRequest, EventInfo eventInfo, Map<String, String[]> queryParams) {
		SDKEventHandler eventHandler = getHandlerFor(eventInfo);
		if (events.eventShouldBeHandledAsync(eventInfo)) {
			return asyncHandler.handle(eventHandler, consumerKeyUsedByTheRequest, eventInfo, queryParams);
		} else {
			return eventHandler.handle(consumerKeyUsedByTheRequest, eventInfo, queryParams);
		}
	}

	private SDKEventHandler getHandlerFor(EventInfo eventInfo) {
		switch (eventInfo.getType()) {
			case SUBSCRIPTION_ORDER:
				return subscriptionOrderHandler;
			case SUBSCRIPTION_CANCEL:
				return subscriptionCancelHandler;
			case SUBSCRIPTION_CHANGE:
				return subscriptionChangeHandler;
			case SUBSCRIPTION_NOTICE:
				return subscriptionNoticeHandlerFor(eventInfo.getPayload().getNotice().getType());
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
