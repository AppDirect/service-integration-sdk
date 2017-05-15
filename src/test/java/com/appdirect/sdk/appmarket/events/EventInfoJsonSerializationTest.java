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

import static com.appdirect.sdk.appmarket.events.AccountStatus.INITIALIZED;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_ORDER;
import static com.appdirect.sdk.support.ContentOf.resourceAsString;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EventInfoJsonSerializationTest {
	private ObjectMapper jsonMapper = getTheSameJsonMapperThatSpringUses();

	@Test
	public void canDeserializeOrderWithInitializedAccount() throws Exception {
		EventInfo eventInfo = jsonMapper.readValue(resourceAsString("events/subscription-order-addon.json"), EventInfo.class);

		assertThat(eventInfo.getType()).isEqualTo(SUBSCRIPTION_ORDER);
		assertThat(eventInfo.getFlag()).isNull();
		assertThat(eventInfo.getPayload().getAccount()).isEqualTo(anInitializedAccount(null, "the-account-id-of-the-main-app"));
	}

	private AccountInfo anInitializedAccount(String accountIdentifier, String parentAccountIdentifier) {
		return AccountInfo.builder().accountIdentifier(accountIdentifier).parentAccountIdentifier(parentAccountIdentifier).status(INITIALIZED).build();
	}

	private ObjectMapper getTheSameJsonMapperThatSpringUses() {
		return Jackson2ObjectMapperBuilder.json().build();
	}
}
