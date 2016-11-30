package com.appdirect.sdk.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.appdirect.sdk.support.UtilityClassesAreWellDefined;

public class EventIdExtractorTest {
	@Test
	public void isWellDefinedUtilityClass() throws Exception {
		UtilityClassesAreWellDefined.verify(EventIdExtractor.class);
	}

	@Test
	public void verifyId_isParsedWithoutParams() {
		String id = EventIdExtractor.extractId("https://base.com/api/integration/v1/events/id?param=1234");
		assertThat(id).isEqualTo("id");
	}
}
