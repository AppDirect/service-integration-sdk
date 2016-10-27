package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.UsageBean;

public interface BillingService {
	APIResult billUsage(String baseURl, String key, String secret, UsageBean usage);
}
