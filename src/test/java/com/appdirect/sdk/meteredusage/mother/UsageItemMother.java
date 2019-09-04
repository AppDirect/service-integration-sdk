package com.appdirect.sdk.meteredusage.mother;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import com.appdirect.sdk.appmarket.events.PricingUnit;
import com.appdirect.sdk.meteredusage.model.UsageItem;
import com.appdirect.sdk.utils.ConstantUtils;
import com.google.inject.internal.Maps;

public class UsageItemMother {
	public static UsageItem.UsageItemBuilder basic() {
		return UsageItem.builder()
				.unitPrice(BigDecimal.ONE)
				.quantity(BigDecimal.ONE)
				.description(ConstantUtils.DESCRIPTION)
				.currency(Currency.getInstance(Locale.US))
				.customUnit(ConstantUtils.CUSTOM_UNIT)
				.pricingUnit(PricingUnit.UNIT)
				.attributes(Maps.newHashMap());
	}
}
