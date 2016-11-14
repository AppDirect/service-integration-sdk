package com.appdirect.sdk.appmarket.api;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountInfo {
	private String accountIdentifier;
	private String status;
}
