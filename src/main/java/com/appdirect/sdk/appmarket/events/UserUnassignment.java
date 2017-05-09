package com.appdirect.sdk.appmarket.events;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
public class UserUnassignment extends EventWithContext {
	@Getter
	private final String unassignedUserId;

	@Getter
	private final String accountId;

	UserUnassignment(String unassignedUserId,
					 String accountId,
					 String consumerKey,
					 Map<String, String[]> queryParameters,
					 EventFlag eventFlag,
					 String eventId,
					 String marketplaceUrl) {

		super(consumerKey, queryParameters, eventFlag, eventId, marketplaceUrl);
		this.unassignedUserId = unassignedUserId;
		this.accountId = accountId;
	}
}
