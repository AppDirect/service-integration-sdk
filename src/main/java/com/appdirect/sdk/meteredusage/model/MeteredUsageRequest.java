package com.appdirect.sdk.meteredusage.model;

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
public class MeteredUsageRequest {
	private String idempotencyKey;
	private boolean billable;
	private String sourceType;
	private List<MeteredUsageItem> usages = new ArrayList<>();
}

