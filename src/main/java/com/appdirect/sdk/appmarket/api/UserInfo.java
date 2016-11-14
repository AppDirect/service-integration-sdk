package com.appdirect.sdk.appmarket.api;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UserInfo {
	private String uuid;
	private String openId;
	private String email;
	private String firstName;
	private String lastName;
	private String language;
	private String locale;
	private Map<String, String> attributes;
}
