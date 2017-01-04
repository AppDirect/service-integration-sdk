package com.appdirect.sdk.appmarket.events;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class EditionCodeBasedAddonDetectorTest {
	@Test
	public void matchesCode_whenCodeIsPresent() throws Exception {
		EditionCodeBasedAddonDetector addonDetector = new EditionCodeBasedAddonDetector("the-code", "the-other-code");

		assertThat(addonDetector.editionCodeIsRelatedToAddon("the-code")).isTrue();
		assertThat(addonDetector.editionCodeIsRelatedToAddon("the-other-code")).isTrue();
	}

	@Test
	public void doesNotMatchCode_whenCodeIsAbsent() throws Exception {
		EditionCodeBasedAddonDetector addonDetector = new EditionCodeBasedAddonDetector("the-code");

		assertThat(addonDetector.editionCodeIsRelatedToAddon("another-unrelated-code"))
				.as("absent code must not be matched")
				.isFalse();
	}

	@Test
	public void doesNotMatchCode_whenCaseDoesNotMatch() throws Exception {
		EditionCodeBasedAddonDetector addonDetector = new EditionCodeBasedAddonDetector("ALL_CAPS");

		assertThat(addonDetector.editionCodeIsRelatedToAddon("aLL_CAPS"))
				.as("codes differing by case must not be matched")
				.isFalse();
	}

	@Test
	public void doesNotMatchCode_whenCodeIsNull() throws Exception {
		EditionCodeBasedAddonDetector addonDetector = new EditionCodeBasedAddonDetector("some-code");

		assertThat(addonDetector.editionCodeIsRelatedToAddon(null)).isFalse();
	}
}
