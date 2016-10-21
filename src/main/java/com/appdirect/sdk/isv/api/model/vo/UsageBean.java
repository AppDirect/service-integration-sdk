package com.appdirect.sdk.isv.api.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsageBean implements Serializable {
	private static final long serialVersionUID = 895825624088948297L;

	private AccountInfo account;
	private AddonInstanceInfo addonInstance;
	private List<UsageItemBean> items = new ArrayList<>();
}
