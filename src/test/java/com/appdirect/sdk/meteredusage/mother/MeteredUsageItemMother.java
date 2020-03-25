package com.appdirect.sdk.meteredusage.mother;

import java.util.Collections;

import com.appdirect.sdk.meteredusage.model.MeteredUsageItem;
import com.appdirect.sdk.utils.ConstantUtils;

public class MeteredUsageItemMother {
	public static MeteredUsageItem.MeteredUsageItemBuilder basic() {
		return MeteredUsageItem.builder()
				.accountId(ConstantUtils.ACCOUNT_ID)
				.usageList(Collections.singletonList(UsageItemMother.basic().build()));
	}

	public static MeteredUsageItem.MeteredUsageItemBuilder withSubscriptionId() {
		return basic().subscriptionId(ConstantUtils.SUBSCRIPTION_ID);
	}
}
