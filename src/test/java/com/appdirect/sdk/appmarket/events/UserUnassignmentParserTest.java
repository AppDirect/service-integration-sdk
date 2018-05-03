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

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserUnassignmentParserTest {
	private UserUnassignmentParser testedEventParser;

	@Before
	public void setUp() throws Exception {
		testedEventParser = new UserUnassignmentParser();
	}

	@Test
	public void testParse_whenParsingAnEventInfoInstanceOfTypeUserUnassign_generateTheAppropriateRichEventObject() throws Exception {
		//Given
		String expectedAccountId = "expectedAccountId";
		String expectedAssignedUserId = "expectedAssignedUserId";
		String expectedConsumerKey = "expectedConsumerKey";
		HashMap<String, String[]> expectedQueryParams = new HashMap<>();
		UserInfo userInfo = UserInfo.builder().uuid(expectedAssignedUserId).build();
		EventFlag expectedEventFlag = null;
		String expectedEventId = "expectedEventId";
		String expectedBaseUrl = "http://www.example.com";
		HashMap<String, String> expectedConfiguration= new HashMap<>();
		UserUnassignment expectedRichEvent = new UserUnassignment(
				userInfo,
				expectedAccountId, 
				expectedConsumerKey, 
				expectedQueryParams, 
				expectedEventFlag, 
				expectedEventId, 
				expectedBaseUrl,
				expectedConfiguration
		);

		EventInfo testEventInfo = userUnassignmentEvent(expectedAccountId, expectedAssignedUserId, expectedEventId, expectedBaseUrl);
		EventHandlingContext testEventHandlingContext = new EventHandlingContext(expectedConsumerKey, expectedQueryParams);


		//When
		UserUnassignment parsedRichEvent = testedEventParser.parse(testEventInfo, testEventHandlingContext);

		//Then
		assertThat(parsedRichEvent).isEqualTo(expectedRichEvent);
	}

	private EventInfo userUnassignmentEvent(String accountIdentifier, String userIdentifier, String eventToken, String baseUrl) {
		return EventInfo.builder()
			.type(EventType.USER_ASSIGNMENT)
			.marketplace(new MarketInfo("APPDIRECT", baseUrl))
			.payload(EventPayload.builder()
				.account(AccountInfo.builder()
					.accountIdentifier(accountIdentifier)
					.build())
				.user(UserInfo.builder()
					.uuid(userIdentifier)
					.build())
				.build())
			.id(eventToken)
			.build();
	}
}
