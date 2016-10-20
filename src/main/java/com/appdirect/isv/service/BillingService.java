package com.appdirect.isv.service;

import com.appdirect.isv.api.model.vo.APIResult;
import com.appdirect.isv.api.model.vo.UsageBean;

public interface BillingService {
	APIResult billUsage(String baseURl, String key, String secret, UsageBean usage);
}
