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
				null
		);

		assertThat(event.getQueryParameters()).isNotNull().isEmpty();
	}
}
