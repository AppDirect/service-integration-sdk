package com.appdirect.sdk.marketplace;

import com.appdirect.sdk.marketplace.api.vo.APIResult;
import com.appdirect.sdk.marketplace.api.vo.UsageBean;

public interface BillingService {
	APIResult billUsage(String baseURl, String key, String secret, UsageBean usage);
}
