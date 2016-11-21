package com.appdirect.sdk.appmarket.api;

public class SubscriptionUpcomingInvoice {
	private final AccountInfo accountInfo;

	public SubscriptionUpcomingInvoice(AccountInfo accountInfo) {

		this.accountInfo = accountInfo;
	}

	public AccountInfo getAccountInfo() {
		return accountInfo;
	}
}
