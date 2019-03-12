package com.appdirect.sdk.vendorFields.model;

import java.util.Arrays;
import java.util.stream.Stream;

public enum OperationType {
	SUBSCRIPTION_CHANGE("SUBSCRIPTION_CHANGE"), SUBSCRIPTION_ORDER("SUBSCRIPTION_ORDER");

	private String value;

	private OperationType(String value) {
		this.value = value;
	}

	public static OperationType fromValue(String value) {
		return Stream.of(values())
			.filter(operationType -> operationType.value.equalsIgnoreCase(value))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(
				"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values())));
	}
}
