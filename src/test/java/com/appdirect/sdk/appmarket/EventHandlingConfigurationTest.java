package com.appdirect.sdk.appmarket;

import static com.appdirect.sdk.appmarket.api.EventType.SUBSCRIPTION_CANCEL;
import static com.appdirect.sdk.appmarket.api.EventType.SUBSCRIPTION_ORDER;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.appdirect.sdk.appmarket.api.SubscriptionCancel;
import com.appdirect.sdk.appmarket.api.SubscriptionOrder;

@RunWith(MockitoJUnitRunner.class)
public class EventHandlingConfigurationTest {
	@Mock
	private AppmarketEventHandler<SubscriptionOrder> subscriptionOrderHandler;
	@Mock
	private AppmarketEventHandler<SubscriptionCancel> subscriptionCancelHandler;

	private EventHandlingConfiguration config;

	@Before
	public void setUp() throws Exception {
		config = new EventHandlingConfiguration(subscriptionOrderHandler, subscriptionCancelHandler);
	}

	@Test
	public void testAllHandlers_mandatoryEventsHaveHandlers() throws Exception {
		assertThat(config.allHandlers())
				.containsKeys(SUBSCRIPTION_ORDER, SUBSCRIPTION_CANCEL);
	}
}
