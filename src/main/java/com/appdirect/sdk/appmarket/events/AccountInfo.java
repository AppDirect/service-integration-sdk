package com.appdirect.sdk.appmarket.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents an "account" (the equivalent of a subscription, from the AppMarket point of view)
 */
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Getter
public class AccountInfo {
	private final String accountIdentifier;
	private final AccountStatus status;
	private final String parentAccountIdentifier;
}
