package com.appdirect.sdk.appmarket.api;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class EventPayload {
	private UserInfo user;
	private CompanyInfo company;
	private AccountInfo account;
	private AddonInstanceInfo addonInstance;
	private AddonBindingInfo addonBinding;
	private OrderInfo order;
	private NoticeInfo notice;
	private HashMap<String, String> configuration = new HashMap<>();
}
