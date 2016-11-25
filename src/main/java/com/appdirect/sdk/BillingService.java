package com.appdirect.sdk;

public interface BillingService {
	APIResult billUsage(String baseURl, String key, String secret, Usage usage);
}
