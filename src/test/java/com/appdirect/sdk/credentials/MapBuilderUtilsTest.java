package com.appdirect.sdk.credentials;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.entry;

import java.util.Map;

import org.junit.Test;

public class MapBuilderUtilsTest {

	@Test
	public void canParse_single_wellFormedEntry() throws Exception {
		Map<String, String> parsedMap = MapBuilderUtils.fromCommaDelimitedKeyValuePairs("k1:s1");

		assertThat(parsedMap).containsOnly(entry("k1", "s1"));
	}

	@Test
	public void canParse_multiple_wellFormedEntry() throws Exception {
		Map<String, String> parsedMap = MapBuilderUtils.fromCommaDelimitedKeyValuePairs("k1:s1,k2:s2,k3:s3");

		assertThat(parsedMap).containsOnly(entry("k1", "s1"), entry("k2", "s2"), entry("k3", "s3"));
	}

	@Test
	public void canParse_multiple_wellFormedEntry_with_trailing_comma() throws Exception {
		Map<String, String> parsedMap = MapBuilderUtils.fromCommaDelimitedKeyValuePairs("k1:s1,k2:s2,k3:s3,");

		assertThat(parsedMap).containsOnly(entry("k1", "s1"), entry("k2", "s2"), entry("k3", "s3"));
	}

	@Test
	public void errorsOut_when_singleMalformedEntry() throws Exception {
		assertThatThrownBy(() -> MapBuilderUtils.fromCommaDelimitedKeyValuePairs("k1:s1:3"))
			.hasMessage("This pair is broken: 'k1:s1:3'. Separate your key from your value with a ':'.")
			.isInstanceOf(IllegalArgumentException.class);
	}
}
