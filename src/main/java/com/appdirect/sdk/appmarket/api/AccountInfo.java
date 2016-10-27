package com.appdirect.sdk.appmarket.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class AccountInfo {
	private String accountIdentifier;
	private String status;
}
