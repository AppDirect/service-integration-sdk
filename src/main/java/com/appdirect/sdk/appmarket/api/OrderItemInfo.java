package com.appdirect.sdk.appmarket.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class OrderItemInfo {
	private PricingUnit unit;
	private int quantity;
}
