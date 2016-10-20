package com.appdirect.isv.api.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

import com.appdirect.isv.api.model.type.PricingDuration;

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
