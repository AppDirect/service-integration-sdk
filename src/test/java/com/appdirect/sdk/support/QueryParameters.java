package com.appdirect.sdk.support;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

public class QueryParameters {

	public static Map<String, String[]> oneQueryParam() {
		return oneQueryParam("param1", "value1");
	}

	public static Map<String, String[]> oneQueryParam(String key, String... values) {
		return queryParams(entryFor(key, values));
	}

	@SafeVarargs
	@SuppressWarnings("varargs")
	public static Map<String, String[]> queryParams(Map.Entry<String, String[]>... keyValuesPair) {
		return stream(keyValuesPair).collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public static SimpleEntry<String, String[]> entryFor(String key, String... values) {
		return new SimpleEntry<>(key, values);
	}

}
