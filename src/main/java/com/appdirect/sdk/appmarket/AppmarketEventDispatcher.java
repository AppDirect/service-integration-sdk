package com.appdirect.sdk.appmarket;

import static com.appdirect.sdk.appmarket.api.ErrorCode.CONFIGURATION_ERROR;
import static java.lang.String.format;

import lombok.RequiredArgsConstructor;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventInfo;

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
			case SUBSCRIPTION_NOTICE: //NOSONAR
				switch (eventInfo.getPayload().getNotice().getType()) {
					case CLOSED:
						return subscriptionClosedHandler.handle(consumerKeyUsedByTheRequest, eventInfo);
					case DEACTIVATED:
						return subscriptionDeactivatedHandler.handle(consumerKeyUsedByTheRequest, eventInfo);
					case REACTIVATED:
						return subscriptionReactivatedHandler.handle(consumerKeyUsedByTheRequest, eventInfo);
					case UPCOMING_INVOICE:
						return subscriptionUpcomingInvoiceHandler.handle(consumerKeyUsedByTheRequest, eventInfo);
					default:
						return unknownEventHandler().handle(consumerKeyUsedByTheRequest, eventInfo);
				}
			default:
				return unknownEventHandler().handle(consumerKeyUsedByTheRequest, eventInfo);
		}

	}

	private SDKEventHandler unknownEventHandler() {
		return (consumerKeyUsedByTheRequest, event) -> new APIResult(CONFIGURATION_ERROR, format("Unsupported event type %s", event.getType()));
	}
}
