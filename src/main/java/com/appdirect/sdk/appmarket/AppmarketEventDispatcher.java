package com.appdirect.sdk.appmarket;

import static com.appdirect.sdk.appmarket.api.ErrorCode.CONFIGURATION_ERROR;
import static java.lang.String.format;

import lombok.RequiredArgsConstructor;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.NoticeType;

@RequiredArgsConstructor
public class AppmarketEventDispatcher {
	private final SDKEventHandler subscriptionOrderHandler;
	private final SDKEventHandler subscriptionCancelHandler;
	private final SDKEventHandler subscriptionChangeHandler;
	private final SDKEventHandler subscriptionDeactivatedHandler;
	private final SDKEventHandler subscriptionReactivatedHandler;
	private final SDKEventHandler subscriptionClosedHandler;
	private final SDKEventHandler subscriptionUpcomingInvoiceHandler;

	public APIResult dispatchAndHandle(String consumerKeyUsedByTheRequest, EventInfo eventInfo) { //NOSONAR
		switch (eventInfo.getType()) {
			case SUBSCRIPTION_ORDER:
				return subscriptionOrderHandler.handle(consumerKeyUsedByTheRequest, eventInfo);
			case SUBSCRIPTION_CANCEL:
				return subscriptionCancelHandler.handle(consumerKeyUsedByTheRequest, eventInfo);
			case SUBSCRIPTION_CHANGE:
				return subscriptionChangeHandler.handle(consumerKeyUsedByTheRequest, eventInfo);
			case SUBSCRIPTION_NOTICE:
				return subscriptionNoticeHandlerFor(eventInfo.getPayload().getNotice().getType())
					.handle(consumerKeyUsedByTheRequest, eventInfo);
			default:
				return unknownEventHandler().handle(consumerKeyUsedByTheRequest, eventInfo);
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
				return unknownEventHandler();
		}
	}

	private SDKEventHandler unknownEventHandler() {
		return (consumerKeyUsedByTheRequest, event) -> new APIResult(CONFIGURATION_ERROR, format("Unsupported event type %s", event.getType()));
	}
}
