package com.appdirect.sdk.appmarket.events;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * A developer-facing event representing cancellation of an addon account requested by the AppMarket
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class AddonSubscriptionCancel extends EventWithContext {
	private final String accountIdentifier;
	private final String parentAccountIdentifier;

	public AddonSubscriptionCancel(String accountIdentifier,
								   String parentAccountIdentifier,
								   String consumerKeyUsedByTheRequest,
								   Map<String, String[]> queryParameters,
								   EventFlag flag,
								   String eventToken,
								   String marketplaceUrl) {

		super(consumerKeyUsedByTheRequest, queryParameters, flag, eventToken, marketplaceUrl);
		this.accountIdentifier = accountIdentifier;
		this.parentAccountIdentifier = parentAccountIdentifier;
	}
}
