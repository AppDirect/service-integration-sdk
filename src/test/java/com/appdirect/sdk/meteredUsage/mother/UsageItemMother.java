package com.appdirect.sdk.meteredUsage.mother;

import com.appdirect.sdk.meteredUsage.model.UsageItem;
import com.appdirect.sdk.utils.ConstantUtils;
import com.google.inject.internal.Lists;

public class UsageItemMother {
	public static UsageItem.UsageItemBuilder basic() {
		return UsageItem.builder()
				.unitPrice(ConstantUtils.UNIT_PRICE)
				.quantity(ConstantUtils.QUANTITY)
				.description(ConstantUtils.DESCRIPTION)
				.currency(ConstantUtils.CURRENCY)
				.customUnit(ConstantUtils.CUSTOM_UNIT)
				.pricingUnit(ConstantUtils.PRICING_UNIT)
				.attributesList(Lists.newArrayList(AttributeItemMother.basic().build()));
	}
}
