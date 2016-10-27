package com.appdirect.sdk.appmarket.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class AccountInfo {
	private String accountIdentifier;
	private String status;
}
