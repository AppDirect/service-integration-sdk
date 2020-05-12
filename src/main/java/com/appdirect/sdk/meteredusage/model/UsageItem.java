package com.appdirect.sdk.meteredusage.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.appdirect.sdk.appmarket.events.PricingUnit;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsageItem {
	private PricingUnit pricingUnit;
	private String customUnit;
	private BigDecimal quantity;
	private BigDecimal unitPrice;
	private String description;
	private Currency currency;
	private Map<String, String> attributes;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	private ZonedDateTime eventDate;
	private String eventId;
}
