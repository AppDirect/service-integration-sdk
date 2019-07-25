package com.appdirect.sdk.meteredUsage.mother;

import java.util.HashMap;

import com.appdirect.sdk.meteredUsage.model.AttributeItem;
import com.appdirect.sdk.utils.ConstantUtils;

public class AttributeItemMother {
	public static AttributeItem.AttributeItemBuilder basic() {
		return AttributeItem.builder()
				.attributes(new HashMap<String, String>() {{
					put(ConstantUtils.ATTRIBUTE_KEY, ConstantUtils.ATTRIBUTE_VALUE);
				}});
	}
}
