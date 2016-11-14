package com.appdirect.sdk.appmarket.api;

import java.util.Map;

import lombok.Builder;
import lombok.Value;

@Value
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
