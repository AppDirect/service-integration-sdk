/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
