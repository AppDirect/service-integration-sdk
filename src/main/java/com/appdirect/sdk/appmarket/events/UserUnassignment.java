package com.appdirect.sdk.appmarket.events;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
public class UserUnassignment extends EventWithConsumerKeyQueryParametersAndEventFlag {
	@Getter
	private final String unassignedUsedId;

	@Getter
	private final String accountId;

	UserUnassignment(String unassignedUsedId,
					 String accountId,
					 String consumerKey,
					 Map<String, String[]> queryParameters,
					 EventFlag eventFlag) {

		super(consumerKey, queryParameters, eventFlag);
		this.unassignedUsedId = unassignedUsedId;
		this.accountId = accountId;
	}
}
