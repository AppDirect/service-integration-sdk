package com.appdirect.sdk.appmarket.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Information about a company. Generally, used to pass information about the company placing an order
 * via a {@link SubscriptionOrder} event
 */
@Getter
@ToString
@AllArgsConstructor
@Builder
public class CompanyInfo {
	private String uuid;
	private String name;
	private String email;
	private String phoneNumber;
	private String website;
	private String country;
}
