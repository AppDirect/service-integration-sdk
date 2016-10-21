package com.appdirect.sdk.isv.service;

import com.appdirect.sdk.isv.api.model.vo.APIResult;
import com.appdirect.sdk.isv.api.model.vo.UsageBean;

public interface BillingService {
	APIResult billUsage(String baseURl, String key, String secret, UsageBean usage);
}
