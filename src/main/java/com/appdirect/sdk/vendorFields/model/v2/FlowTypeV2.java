package com.appdirect.sdk.vendorFields.model.v2;

import java.util.Arrays;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FlowTypeV2 {
	SELF("SELF"), BOBO("BOBO");

	private String value;

	public static FlowTypeV2 fromValue(String value) {
		return Stream.of(values())
				.filter(flowType -> flowType.value.equalsIgnoreCase(value))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(
						"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values())));
	}
}
