package com.appdirect.sdk.marketplace;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.collections.Sets.newSet;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.appdirect.sdk.exception.IsvServiceException;
import com.appdirect.sdk.marketplace.api.type.EventType;

public class MarketplaceEventProcessorRegistryTest {
	private MarketplaceEventProcessor theSoleProcessorInTheRegistry;
	private MarketplaceEventProcessorRegistry registry;

	@BeforeMethod
	public void setUp() throws Exception {
		theSoleProcessorInTheRegistry = mock(MarketplaceEventProcessor.class);
		registry = new MarketplaceEventProcessorRegistry(newSet(theSoleProcessorInTheRegistry));
	}

	@Test
	public void whenProcessorForThisEventExistsItIsReturned() {
		when(theSoleProcessorInTheRegistry.supports(EventType.SUBSCRIPTION_ORDER)).thenReturn(true);

		MarketplaceEventProcessor processorMatch = registry.get(EventType.SUBSCRIPTION_ORDER);

		assertThat(processorMatch).isEqualTo(theSoleProcessorInTheRegistry);
	}

	@Test
	public void whenGetIsCalledAndNoProcessorsSupportTheTenantIsvServiceExceptionIsThrown() {
		when(theSoleProcessorInTheRegistry.supports(EventType.SUBSCRIPTION_ORDER)).thenReturn(false);

		assertThatThrownBy(() -> registry.get(EventType.SUBSCRIPTION_ORDER)).isInstanceOf(IsvServiceException.class);
	}
}
