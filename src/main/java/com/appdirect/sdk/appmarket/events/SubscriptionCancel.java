package com.appdirect.sdk.appmarket.events;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * A developer-facing event representing cancellation of an account requested by the AppMarket
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class SubscriptionCancel extends EventWithContext {
	private final String accountIdentifier;

	public SubscriptionCancel(String consumerKeyUsedByTheRequest,
							  String accountIdentifier,
							  Map<String, String[]> queryParameters,
							  EventFlag flag,
							  String eventId) {

		super(consumerKeyUsedByTheRequest, queryParameters, flag, eventId);
		this.accountIdentifier = accountIdentifier;
	}
}
