package com.appdirect.sdk.appmarket.events;

import lombok.ToString;

@ToString
public class UserAssignmentParser implements EventParser<UserAssignment> {
	@Override
	public UserAssignment parse(EventInfo eventInfo, EventHandlingContext eventContext) {
		return new UserAssignment(
			eventInfo.getPayload().getUser(),
			eventInfo.getPayload().getAccount().getAccountIdentifier(),
			eventContext.getConsumerKeyUsedByTheRequest(),
			eventContext.getQueryParameters(),
			eventInfo.getFlag(),
			eventInfo.getId()
		);
	}
}
