package com.appdirect.sdk.meteredusage.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeteredUsageItem {
	private String accountId;
	@JsonInclude(Include.NON_NULL)
	private String subscriptionId;
	private List<UsageItem> usageList = new ArrayList<>();
}
