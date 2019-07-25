package com.appdirect.sdk.meteredUsage.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsageItem {
	private String pricingUnit;
	private String customUnit;
	private String quantity;
	private String unitPrice;
	private String description;
	private String currency;
	private List<AttributeItem> attributesList;
}

