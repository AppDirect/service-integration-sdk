package com.appdirect.sdk.appmarket.api;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Containts all relevant information of an order placed using a {@link SubscriptionOrder} event
 */
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class OrderInfo {
	private String editionCode;
	private String addonOfferingCode;
	private PricingDuration pricingDuration;
	private List<OrderItemInfo> items = new ArrayList<>();
}
