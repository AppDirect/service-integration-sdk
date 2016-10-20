package com.appdirect.isv.api.model.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.appdirect.isv.api.model.type.PricingUnit;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemInfo implements Serializable {
	private static final long serialVersionUID = -4470539696272892175L;

	private PricingUnit unit;
	private int quantity;
}
