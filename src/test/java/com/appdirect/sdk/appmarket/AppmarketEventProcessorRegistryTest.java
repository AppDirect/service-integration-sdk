package com.appdirect.sdk.appmarket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.util.collections.Sets.newSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.appdirect.sdk.appmarket.api.EventType;
import com.appdirect.sdk.appmarket.api.SubscriptionOrder;
import com.appdirect.sdk.exception.DeveloperServiceException;

public class AppmarketEventProcessorRegistryTest {
	@Mock
	private AppmarketEventProcessor<SubscriptionOrder> theSoleProcessorInTheRegistry;
	private AppmarketEventProcessorRegistry registry;

	@Before
	public void setUp() throws Exception {
		initMocks(this);

		registry = new AppmarketEventProcessorRegistry(newSet(theSoleProcessorInTheRegistry));
	}

	@Test
	public void whenProcessorForThisEventExistsItIsReturned() {
		when(theSoleProcessorInTheRegistry.supports(EventType.SUBSCRIPTION_ORDER)).thenReturn(true);

		AppmarketEventProcessor<SubscriptionOrder> processorMatch = registry.get(EventType.SUBSCRIPTION_ORDER, SubscriptionOrder.class);

		assertThat(processorMatch).isEqualTo(theSoleProcessorInTheRegistry);
	}

	@Test
	public void whenNoProcessorsSupportTheEventExceptionIsThrown() {
		when(theSoleProcessorInTheRegistry.supports(EventType.SUBSCRIPTION_ORDER)).thenReturn(false);

		assertThatThrownBy(() -> registry.get(EventType.SUBSCRIPTION_ORDER, SubscriptionOrder.class)).isInstanceOf(DeveloperServiceException.class);
	}
}
