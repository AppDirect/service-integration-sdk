package com.appdirect.sdk.appmarket.events;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class UserUnassignment extends EventWithConsumerKeyQueryParametersAndEventFlag {
	private final String unassignedUsedId;
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
