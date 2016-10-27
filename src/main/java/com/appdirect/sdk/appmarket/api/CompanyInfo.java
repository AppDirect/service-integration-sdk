package com.appdirect.sdk.appmarket.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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
