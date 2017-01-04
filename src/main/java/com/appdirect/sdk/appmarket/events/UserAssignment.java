package com.appdirect.sdk.appmarket.events;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class UserAssignment extends EventWithConsumerKeyQueryParametersAndEventFlag {
	private final String assignedUserId;
	private final String accountId;

	public UserAssignment(String assignedUserId, String accountId, String consumerKey, Map<String, String []> queryParameters, EventFlag eventFlag) {
		super(consumerKey, queryParameters, eventFlag);
		this.assignedUserId = assignedUserId;
		this.accountId = accountId;
	}
}
