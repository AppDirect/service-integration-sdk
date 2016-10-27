package com.appdirect.sdk.appmarket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.collections.Sets.newSet;

import org.junit.Before;
import org.junit.Test;

import com.appdirect.sdk.appmarket.api.EventType;
import com.appdirect.sdk.exception.IsvServiceException;

public class AppmarketEventProcessorRegistryTest {
	private AppmarketEventProcessor theSoleProcessorInTheRegistry;
	private AppmarketEventProcessorRegistry registry;

	@Before
	public void setUp() throws Exception {
		theSoleProcessorInTheRegistry = mock(AppmarketEventProcessor.class);
		registry = new AppmarketEventProcessorRegistry(newSet(theSoleProcessorInTheRegistry));
	}

	@Test
	public void whenProcessorForThisEventExistsItIsReturned() {
		when(theSoleProcessorInTheRegistry.supports(EventType.SUBSCRIPTION_ORDER)).thenReturn(true);

		AppmarketEventProcessor processorMatch = registry.get(EventType.SUBSCRIPTION_ORDER);

		assertThat(processorMatch).isEqualTo(theSoleProcessorInTheRegistry);
	}

	@Test
	public void whenGetIsCalledAndNoProcessorsSupportTheTenantIsvServiceExceptionIsThrown() {
		when(theSoleProcessorInTheRegistry.supports(EventType.SUBSCRIPTION_ORDER)).thenReturn(false);

		assertThatThrownBy(() -> registry.get(EventType.SUBSCRIPTION_ORDER)).isInstanceOf(IsvServiceException.class);
	}
}
