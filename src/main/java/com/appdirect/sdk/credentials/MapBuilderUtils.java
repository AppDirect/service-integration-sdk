package com.appdirect.sdk.credentials;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.util.Assert;

/**
 * Builds string maps out of raw strings.
 */
class MapBuilderUtils {

	private MapBuilderUtils() {
	}

	/**
	 * Parses comma-delimited lists of colon-delimited key-value pairs.
	 * i.e. <code>key1:value1,color:red,favorite-fruit:banana</code>
	 * Trailing comma is optional.
	 *
	 * @param commaDelimitedKeyValuePairs string to parse, i.e. <code>key1:value1,key2:value2</code>
	 * @return The map version of this string
	 */
	static Map<String, String> fromCommaDelimitedKeyValuePairs(String commaDelimitedKeyValuePairs) {
		return Stream.of(commaDelimitedKeyValuePairs.split(","))
			.collect(toMap(
				MapBuilderUtils::extractDeveloperKey,
				MapBuilderUtils::extractDeveloperSecret
			));
	}

	private static String extractDeveloperKey(String columnSeparatedKeyValuePair) {
		String[] kv = columnSeparatedKeyValuePair.split(":");
		Assert.isTrue(
			kv.length == 2,
			format("This pair is broken: '%s'. Separate your key from your value with a ':'.", columnSeparatedKeyValuePair)
		);
		return kv[0];
	}

	private static String extractDeveloperSecret(String columnSeparatedKeyValuePair) {
		String[] kv = columnSeparatedKeyValuePair.split(":");
		Assert.isTrue(
			kv.length == 2,
			format("This pair is broken: '%s'. Separate your key from your value with a ':'.", columnSeparatedKeyValuePair)
		);
		return kv[1];
	}
}
