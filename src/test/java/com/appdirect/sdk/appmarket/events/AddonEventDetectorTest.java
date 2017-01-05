package com.appdirect.sdk.appmarket.events;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class AddonEventDetectorTest {
	private AddonEventDetector addonDetector = new AddonEventDetector();

	@Test
	public void eventIsFlaggedAsAddonRelated_whenParentAccountId_isPresent() throws Exception {
		assertThat(addonDetector.eventIsRelatedToAddon(anEventWithParentAccountId())).isTrue();
	}

	@Test
	public void eventIsNotFlaggedAsAddonRelated_whenParentAccountId_isAbsent() throws Exception {
		assertThat(addonDetector.eventIsRelatedToAddon(anEventWithNoParentAccountId())).isFalse();
	}

	private EventInfo anEventWithParentAccountId() {
		return EventInfo.builder()
				.payload(EventPayload.builder()
						.account(AccountInfo.builder().parentAccountIdentifier("some-parent-account-id").build())
						.build())
				.build();
	}

	private EventInfo anEventWithNoParentAccountId() {
		return EventInfo.builder().build();
	}
}
