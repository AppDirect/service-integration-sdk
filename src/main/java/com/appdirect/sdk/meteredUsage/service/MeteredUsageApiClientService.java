package com.appdirect.sdk.meteredUsage.service;

import java.util.List;

import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.meteredUsage.model.MeteredUsageItem;

public interface MeteredUsageApiClientService {

	APIResult reportUsage(String baseUrl, String key, String idempotentKey, MeteredUsageItem meteredUsageItem);

	APIResult reportUsage(String baseUrl, String key, String idempotentKey, List<MeteredUsageItem> meteredUsageItem);
}
