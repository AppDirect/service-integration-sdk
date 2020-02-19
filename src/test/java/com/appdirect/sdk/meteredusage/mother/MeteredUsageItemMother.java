package com.appdirect.sdk.meteredusage.mother;

import com.appdirect.sdk.meteredusage.model.MeteredUsageItem;
import com.appdirect.sdk.utils.ConstantUtils;
import com.google.inject.internal.Lists;

public class MeteredUsageItemMother {
	public static MeteredUsageItem.MeteredUsageItemBuilder basic() {
		return MeteredUsageItem.builder()
				.accountId(ConstantUtils.ACCOUNT_ID)
				.usageList(Lists.newArrayList(UsageItemMother.basic().build()));
	}

	public static MeteredUsageItem.MeteredUsageItemBuilder withSubscriptionId() {
		return MeteredUsageItem.builder()
			.accountId(ConstantUtils.ACCOUNT_ID)
			.subscriptionId(ConstantUtils.SUBSCRIPTION_ID)
			.usageList(Lists.newArrayList(UsageItemMother.basic().build()));
	}
}
