package com.appdirect.sdk.appmarket.api;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class Usage {
	private AccountInfo account;
	private AddonInstanceInfo addonInstance;
	private List<UsageItem> items = new ArrayList<>();
}
