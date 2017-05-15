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

import static com.appdirect.sdk.appmarket.events.EventType.ADDON_ORDER;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_CANCEL;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_CHANGE;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_NOTICE;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_ORDER;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class EventsTest {

	private Events events = new Events();

	@Test
	public void allEventsShouldBeHandledAsync_butSubscriptionNotice() throws Exception {
		assertThat(events.eventShouldBeHandledAsync(eventOfType(SUBSCRIPTION_ORDER))).isTrue();
		assertThat(events.eventShouldBeHandledAsync(eventOfType(SUBSCRIPTION_CANCEL))).isTrue();
		assertThat(events.eventShouldBeHandledAsync(eventOfType(SUBSCRIPTION_CHANGE))).isTrue();
		assertThat(events.eventShouldBeHandledAsync(eventOfType(ADDON_ORDER))).isTrue();

		assertThat(events.eventShouldBeHandledAsync(eventOfType(SUBSCRIPTION_NOTICE))).isFalse();
	}

	@Test
	public void extractEditionCode_returnsEmpty_whenEventHasNoEdition() throws Exception {
		assertThat(events.extractEditionCode(eventOfType(SUBSCRIPTION_NOTICE))).isEmpty();
	}

	@Test
	public void extractEditionCode_returnsTheEdition_whenEventHasOne() throws Exception {
		assertThat(events.extractEditionCode(eventWithEdition("some"))).contains("some");
	}

	private EventInfo eventOfType(EventType eventType) {
		return EventInfo.builder().type(eventType).build();
	}

	private EventInfo eventWithEdition(String editionCode) {
		return EventInfo.builder().payload(
				EventPayload.builder().order(
						OrderInfo.builder().editionCode(editionCode).build()).build()).build();
	}
}
