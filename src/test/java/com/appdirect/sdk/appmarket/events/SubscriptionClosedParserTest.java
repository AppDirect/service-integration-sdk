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

import static com.appdirect.sdk.appmarket.events.EventHandlingContexts.defaultEventContext;
import static com.appdirect.sdk.appmarket.events.EventHandlingContexts.eventContext;
import static com.appdirect.sdk.appmarket.events.EventFlag.DEVELOPMENT;
import static com.appdirect.sdk.support.QueryParameters.oneQueryParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.util.Arrays.array;

import org.junit.Test;

public class SubscriptionClosedParserTest {
	private SubscriptionClosedParser parser = new SubscriptionClosedParser();

	@Test
	public void parse_extractsTheConsumerKey() throws Exception {
		SubscriptionClosed parsedEvent = parser.parse(someEvent(), eventContext("the-key"));

		assertThat(parsedEvent.getConsumerKeyUsedByTheRequest()).isEqualTo("the-key");
	}

	@Test
	public void parse_extractsTheAccountInfo() throws Exception {
		SubscriptionClosed parsedEvent = parser.parse(someEventFor("big-account").build(), defaultEventContext());

		assertThat(parsedEvent.getAccountInfo().getAccountIdentifier()).isEqualTo("big-account");
	}

	@Test
	public void parse_extractsTheDevelopmentFlag() throws Exception {
		SubscriptionClosed parsedEvent = parser.parse(someEventFor("big-account").flag(DEVELOPMENT).build(), defaultEventContext());

		assertThat(parsedEvent.isDevelopment()).isTrue();
	}

	@Test
	public void parse_extractsTheQueryParams() throws Exception {
		SubscriptionClosed parsedEvent = parser.parse(someEvent(), eventContext("the-key", oneQueryParam("some", "params", "1")));

		assertThat(parsedEvent.getQueryParameters()).containsOnly(entry("some", array("params", "1")));
	}

	private EventInfo someEvent() {
		return someEventFor("some-account").build();
	}

	private EventInfo.EventInfoBuilder someEventFor(String accountId) {
		return EventInfo.builder()
				.marketplace(new MarketInfo("APPDIRECT", "http://example.com"))
				.payload(
						EventPayload.builder()
							.account(
									AccountInfo.builder()
									.accountIdentifier(accountId)
									.build()
							).build()
				);
	}
}
