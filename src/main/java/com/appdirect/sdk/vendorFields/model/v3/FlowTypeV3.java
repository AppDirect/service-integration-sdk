package com.appdirect.sdk.vendorFields.model.v3;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum FlowTypeV3 {
	SELF("SELF"), BOBO("BOBO");

	private String value;

	public static FlowTypeV3 fromValue(String value) {
		return Stream.of(values())
				.filter(flowType -> flowType.value.equalsIgnoreCase(value))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(
						"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values())));
	}
}
