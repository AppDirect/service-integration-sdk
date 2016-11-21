package com.appdirect.sdk.appmarket.api;

public class SubscriptionClosed {
	private final AccountInfo accountInfo;

	public SubscriptionClosed(AccountInfo accountInfo) {

		this.accountInfo = accountInfo;
	}

	public AccountInfo getAccountInfo() {
		return accountInfo;
	}
}
