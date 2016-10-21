package com.appdirect.sdk.isv.api.model.vo;

import java.io.Serializable;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventPayload implements Serializable {
	private static final long serialVersionUID = 3080925569209286979L;

	private UserInfo user;
	private CompanyInfo company;
	private AccountInfo account;
	private AddonInstanceInfo addonInstance;
	private AddonBindingInfo addonBinding;
	private OrderInfo order;
	private NoticeInfo notice;
	private HashMap<String, String> configuration = new HashMap<>();
}
