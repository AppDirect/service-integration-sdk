package com.appdirect.sdk.vendorrequiredfields.model;

import java.util.Arrays;

public enum FlowType {
	END_USER_FLOW("END_USER_FLOW"), RESELLER_FLOW("RESELLER_FLOW");

	private String value;

	private FlowType(String value) {
		this.value = value;
	}

	public static FlowType fromValue(String value) {
		for (FlowType flowType : values()) {
			if (flowType.value.equalsIgnoreCase(value)) {
				return flowType;
			}
		}
		throw new IllegalArgumentException(
			"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
	}
}
