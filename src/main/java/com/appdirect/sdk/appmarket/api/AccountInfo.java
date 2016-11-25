package com.appdirect.sdk.appmarket.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Getter
public class AccountInfo {
	private final String accountIdentifier;
	private final AccountStatus status;
}
