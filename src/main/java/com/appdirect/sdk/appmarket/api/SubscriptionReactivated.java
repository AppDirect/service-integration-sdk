package com.appdirect.sdk.appmarket.api;

public class SubscriptionReactivated {
	private final AccountInfo accountInfo;

	public SubscriptionReactivated(AccountInfo accountInfo) {

		this.accountInfo = accountInfo;
	}

	public AccountInfo getAccountInfo() {
		return accountInfo;
	}
}
