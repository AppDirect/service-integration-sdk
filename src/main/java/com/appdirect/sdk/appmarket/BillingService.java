package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.vo.APIResult;
import com.appdirect.sdk.appmarket.api.vo.UsageBean;

public interface BillingService {
	APIResult billUsage(String baseURl, String key, String secret, UsageBean usage);
}
