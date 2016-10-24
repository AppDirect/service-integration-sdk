package com.appdirect.sdk.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DomainModel implements Serializable {
	private static final long serialVersionUID = 6542116431323964928L;

	private String name;
	private String currency;
	private BigDecimal priceFirstYear;
	private BigDecimal priceRecurringPerYear;
	private boolean available = true;
}
