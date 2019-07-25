package com.appdirect.sdk.meteredusage.mother;

import com.appdirect.sdk.meteredusage.model.AttributeItem;
import com.appdirect.sdk.utils.ConstantUtils;
import wiremock.com.google.common.collect.ImmutableMap;

public class AttributeItemMother {
	public static AttributeItem.AttributeItemBuilder basic() {
		return AttributeItem.builder()
				.attributes(ImmutableMap.of(ConstantUtils.ATTRIBUTE_KEY, ConstantUtils.ATTRIBUTE_VALUE));
	}
}
