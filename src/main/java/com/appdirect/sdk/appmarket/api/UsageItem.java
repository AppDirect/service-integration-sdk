package com.appdirect.sdk.appmarket.api;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class UsageItem {
	private PricingUnit unit;
	private BigDecimal quantity;
	private BigDecimal price;
	private String description;
}
