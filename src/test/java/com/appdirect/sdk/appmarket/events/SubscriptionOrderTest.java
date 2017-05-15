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

import org.junit.Test;

public class SubscriptionOrderTest {
	@Test
	public void getQueryParameters_onEvent_neverReturnsNull() throws Exception {
		SubscriptionOrder event = new SubscriptionOrder(
				"key",
				null,
				UserInfo.builder().build(),
				new HashMap<>(),
				CompanyInfo.builder().build(),
				OrderInfo.builder().build(),
				"partner",
				"appUuid",
				null,
				null,
				null
		);

		assertThat(event.getQueryParameters()).isNotNull().isEmpty();
	}
}
