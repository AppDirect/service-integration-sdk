package com.appdirect.sdk.appmarket.events;

public class UserUnassignmentParser implements EventParser<UserUnassignment> {
	@Override
	public UserUnassignment parse(EventInfo eventInfo, EventHandlingContext eventContext) {
		return new UserUnassignment(
			eventInfo.getPayload().getUser().getUuid(),
			eventInfo.getPayload().getAccount().getAccountIdentifier(),
			eventContext.getConsumerKeyUsedByTheRequest(),
			eventContext.getQueryParameters(),
			eventInfo.getFlag(),
			eventInfo.getId(),
			eventInfo.getMarketplace().getBaseUrl()
		);
	}
}
