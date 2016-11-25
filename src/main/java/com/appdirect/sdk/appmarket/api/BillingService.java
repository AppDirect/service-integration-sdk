package com.appdirect.sdk.appmarket.api;

public interface BillingService {
	APIResult billUsage(String baseURl, String key, String secret, Usage usage);
}
