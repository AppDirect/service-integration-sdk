package com.appdirect.sdk.meteredUsage.mother;

import com.appdirect.sdk.meteredUsage.model.MeteredUsageItem;
import com.appdirect.sdk.utils.ConstantUtils;
import com.google.inject.internal.Lists;

public class MeteredUsageItemMother {
	public static MeteredUsageItem.MeteredUsageItemBuilder basic() {
		return MeteredUsageItem.builder()
				.accountId(ConstantUtils.ACCOUNT_ID)
				.usageList(Lists.newArrayList(UsageItemMother.basic().build()));
	}
}
