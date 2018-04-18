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

import static com.appdirect.sdk.appmarket.events.EventFlag.STATELESS;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AddonSubscriptionCancelEventParserTest {

	private AddonSubscriptionCancelEventParser testedParser = new AddonSubscriptionCancelEventParser();

	@Test
	public void parse_whenAnAddonSubscriptionCancelEventWithNoFlagIsParsed_aCorrespondingRichEventIsCreated() throws Exception {
		//Given
		String expectedConsumerKey = "expectedConsumerKey";
		String expectedAddonAccountIdentifier = "expectedAddonAccountIdentifier";
		String expectedParentAccountIdentifier = "expectedParentAccountIdentifier";
		String expectedEventId = "expectedEventId";
		Map<String, String[]> expectedParameters = new HashMap<>();
		EventFlag expectedFlag = null;
		String expectedBaseUrl = "http://www.example.com";
		Map<String, String> expectedConfiguration = new HashMap<>();
		EventInfo testEvent = addonCancelEvent(expectedAddonAccountIdentifier, expectedParentAccountIdentifier, expectedFlag, expectedEventId, expectedBaseUrl);
		EventHandlingContext testEventContext = EventHandlingContexts.eventContext(expectedConsumerKey, expectedParameters);
		AddonSubscriptionCancel expectedEvent = new AddonSubscriptionCancel(
			expectedAddonAccountIdentifier,
			expectedParentAccountIdentifier,
			expectedConsumerKey,
			expectedParameters,
			expectedFlag,
			expectedEventId,
			expectedBaseUrl,
			expectedConfiguration
		);

		//When
		AddonSubscriptionCancel parsedEvent = testedParser.parse(testEvent, testEventContext);

		//Then
		assertThat(parsedEvent).isEqualTo(expectedEvent);
	}

	@Test
	public void parse_whenAnAddonSubscriptionCancelEventWithFlagIsParsed_aCorrespondingRichEventIsCreated() throws Exception {
		//Given
		String expectedConsumerKey = "expectedConsumerKey";
		String expectedAddonAccountIdentifier = "expectedAddonAccountIdentifier";
		String expectedParentAccountIdentifier = "expectedParentAccountIdentifier";
		Map<String, String[]> expectedParameters = new HashMap<>();
		EventFlag expectedFlag = STATELESS;
		String expectedEventId = "expectedEventId";
		String expectedBaseUrl = "http://www.example.com";
		Map<String, String> expectedConfiguration = new HashMap<>();
		EventInfo testEvent = addonCancelEvent(expectedAddonAccountIdentifier, expectedParentAccountIdentifier, expectedFlag, expectedEventId, expectedBaseUrl);
		EventHandlingContext testEventContext = EventHandlingContexts.eventContext(expectedConsumerKey, expectedParameters);
		AddonSubscriptionCancel expectedEvent = new AddonSubscriptionCancel(
			expectedAddonAccountIdentifier,
			expectedParentAccountIdentifier,
			expectedConsumerKey,
			expectedParameters,
			expectedFlag,
			expectedEventId,
			expectedBaseUrl,
			expectedConfiguration
		);

		//When
		AddonSubscriptionCancel parsedEvent = testedParser.parse(testEvent, testEventContext);

		//Then
		assertThat(parsedEvent).isEqualTo(expectedEvent);
	}

	private EventInfo addonCancelEvent(String accountIdentifier, String parentAccountIdentifier, EventFlag eventFlag, String eventToken, String baseUrl) {
		return EventInfo.builder()
			.flag(eventFlag)
			.marketplace(
				new MarketInfo("APPDIRECT", baseUrl)
			)
			.payload(
				EventPayload.builder()
					.account(
						AccountInfo.builder()
							.accountIdentifier(accountIdentifier)
							.parentAccountIdentifier(parentAccountIdentifier)
							.build()
					).build()
			)
			.id(eventToken)
			.build();
	}

}
