package com.appdirect.sdk.meteredUsage.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeteredUsageItem {
	private String accountId;
	private List<UsageItem> usageList = new ArrayList<>();
}

