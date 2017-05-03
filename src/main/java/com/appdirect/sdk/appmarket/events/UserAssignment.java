package com.appdirect.sdk.appmarket.events;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class UserAssignment extends EventWithConsumerKeyQueryParametersAndEventFlag {
	private final UserInfo assignedUser;
	private final String accountId;

	public UserAssignment(UserInfo assignedUser, String accountId, String consumerKey, Map<String, String []> queryParameters, EventFlag eventFlag) {
		super(consumerKey, queryParameters, eventFlag);
		this.assignedUser = assignedUser;
		this.accountId = accountId;
	}
}
