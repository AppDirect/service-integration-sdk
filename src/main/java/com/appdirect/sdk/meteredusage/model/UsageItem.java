package com.appdirect.sdk.meteredusage.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.appdirect.sdk.appmarket.events.PricingUnit;
import com.fasterxml.jackson.annotation.JsonFormat;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsageItem {
	private PricingUnit pricingUnit;
	private String customUnit;
	private BigDecimal quantity;
	private BigDecimal unitPrice;
	private String description;
	private Currency currency;
	private Map<String, String> attributes;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
	private LocalDateTime eventDate;
	private String eventId;
}

