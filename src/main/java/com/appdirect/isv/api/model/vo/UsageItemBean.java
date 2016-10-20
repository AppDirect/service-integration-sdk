package com.appdirect.isv.api.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

import com.appdirect.isv.api.model.type.PricingUnit;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsageItemBean implements Serializable {
	private static final long serialVersionUID = 4836137752602987252L;

	private PricingUnit unit;
	private BigDecimal quantity;
	private BigDecimal price;
	private String description;
}
