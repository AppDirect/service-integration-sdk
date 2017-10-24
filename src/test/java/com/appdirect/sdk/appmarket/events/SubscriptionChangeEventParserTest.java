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

import static com.appdirect.sdk.appmarket.events.EventFlag.DEVELOPMENT;
import static com.appdirect.sdk.appmarket.events.EventHandlingContexts.eventContext;
import static com.appdirect.sdk.support.QueryParameters.oneQueryParam;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SubscriptionChangeEventParserTest {

	private SubscriptionChangeEventParser testedParser = new SubscriptionChangeEventParser();

	@Test
	public void parses_creatorDetails_orderInfo_accountInfo_consumerKey_devFlag_andQueryParameters() throws Exception {
		//Given
		UserInfo expectedCreatorDetails = UserInfo.builder().build();
		AccountInfo expectedAccountInfo = AccountInfo.builder().build();
		OrderInfo expectedOrderInfo = OrderInfo.builder().build();
		Map<String, String> configuration = new HashMap<>();
		configuration.put("key1", "val1");
		configuration.put("key2", "val2");
		EventInfo testEventInfo = EventInfo.builder()
				.marketplace(new MarketInfo("", ""))
				.flag(DEVELOPMENT)
				.creator(expectedCreatorDetails)
				.payload(EventPayload.builder()
						.account(expectedAccountInfo)
						.order(expectedOrderInfo)
						.configuration(configuration)
						.build())
				.build();

		//When
		SubscriptionChange parsedEvent = testedParser.parse(testEventInfo, eventContext("the-magic-key", oneQueryParam("param1", "value12")));

		//Then
		assertThat(parsedEvent.getOwner()).isEqualTo(expectedCreatorDetails);
		assertThat(parsedEvent.getOrder()).isEqualTo(expectedOrderInfo);
		assertThat(parsedEvent.getAccount()).isEqualTo(expectedAccountInfo);
		assertThat(parsedEvent.getConsumerKeyUsedByTheRequest()).isEqualTo("the-magic-key");
		assertThat(parsedEvent.isDevelopment()).isTrue();
		assertThat(parsedEvent.getQueryParameters()).containsOnly(entry("param1", array("value12")));
		assertThat(parsedEvent.getConfiguration()).isEqualTo(configuration);
	}
}
