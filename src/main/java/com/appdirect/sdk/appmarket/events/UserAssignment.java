package com.appdirect.sdk.appmarket.events;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class UserAssignment extends EventWithContext {
	private final UserInfo assignedUser;
	private final String accountId;

	public UserAssignment(UserInfo assignedUser, 
						  String accountId, 
						  String consumerKey, 
						  Map<String, String []> queryParameters, 
						  EventFlag eventFlag, 
						  String eventId) {

		super(consumerKey, queryParameters, eventFlag, eventId);
		this.assignedUser = assignedUser;
		this.accountId = accountId;
	}
}
