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

import org.springframework.format.annotation.DateTimeFormat;

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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private LocalDateTime eventDate;
	private String eventId;
}

