package com.appdirect.sdk.appmarket.api;

public class SubscriptionDeactivated {
	private final AccountInfo accountInfo;

	public SubscriptionDeactivated(AccountInfo accountInfo) {

		this.accountInfo = accountInfo;
	}

	public AccountInfo getAccountInfo() {
		return accountInfo;
	}
}
