package com.appdirect.sdk.marketplace.api.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.appdirect.sdk.marketplace.api.type.PricingDuration;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo implements Serializable {
	private static final long serialVersionUID = -2844551673053039222L;

	private String editionCode;
	private String addonOfferingCode;
	private PricingDuration pricingDuration;
	private List<OrderItemInfo> items = new ArrayList<>();
}
