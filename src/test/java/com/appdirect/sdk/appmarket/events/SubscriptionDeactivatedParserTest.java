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

import static com.appdirect.sdk.appmarket.events.AccountStatus.SUSPENDED;
import static com.appdirect.sdk.appmarket.events.EventHandlingContexts.eventContext;
import static com.appdirect.sdk.appmarket.events.EventFlag.DEVELOPMENT;
import static com.appdirect.sdk.support.QueryParameters.oneQueryParam;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.Test;

public class SubscriptionDeactivatedParserTest {
	private SubscriptionDeactivatedParser subscriptionDeactivatedParser = new SubscriptionDeactivatedParser();

	@Test
	public void testParse_whenEventInfoContainsASubscriptionDeactivatedMessage_thenParseAsAppropriateDeveloperEventType() throws Exception {
		//Given
		String testConsumerKey = "testConsumerKey";
		String testAccountIdentifier = "testAccountIdentifier";
		AccountStatus testAccountStatus = SUSPENDED;
		String testBaseUrl = "http://example.com";
		EventInfo testEventInfo = createSubscriptionDeactivatedEvent(testAccountIdentifier, testAccountStatus, testBaseUrl);

		Map<String, String[]> testQueryParams = oneQueryParam("hello", "world");

		//When
		SubscriptionDeactivated parsedEvent = subscriptionDeactivatedParser.parse(testEventInfo, eventContext(testConsumerKey, testQueryParams));

		//Then
		assertThat(parsedEvent.getAccountInfo().getAccountIdentifier()).isEqualTo(testAccountIdentifier);
		assertThat(parsedEvent.getAccountInfo().getStatus()).isEqualTo(testAccountStatus);
		assertThat(parsedEvent.getConsumerKeyUsedByTheRequest()).isEqualTo(testConsumerKey);
		assertThat(parsedEvent.isDevelopment()).isTrue();
		assertThat(parsedEvent.getQueryParameters()).isEqualTo(testQueryParams);
	}

	private EventInfo createSubscriptionDeactivatedEvent(String testAccountIdentifier, AccountStatus testAccountStatus, String baseMarketplaceUrl) {
		return EventInfo.builder()
				.marketplace(new MarketInfo("APPDIRECT", baseMarketplaceUrl))
				.flag(DEVELOPMENT)
				.payload(EventPayload.builder()
					.account(
						AccountInfo.builder()
							.accountIdentifier(testAccountIdentifier)
							.status(testAccountStatus)
						.build())
					.build())
				.build();
	}

}
