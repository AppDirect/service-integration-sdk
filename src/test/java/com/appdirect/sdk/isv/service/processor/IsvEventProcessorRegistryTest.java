package com.appdirect.sdk.isv.service.processor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.collections.Sets.newSet;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.appdirect.sdk.isv.api.model.type.EventType;
import com.appdirect.sdk.isv.exception.IsvServiceException;

public class IsvEventProcessorRegistryTest {
	private IsvEventProcessor theSoleProcessorInTheRegistry;
	private IsvEventProcessorRegistry registry;

	@BeforeMethod
	public void setUp() throws Exception {
		theSoleProcessorInTheRegistry = mock(IsvEventProcessor.class);
		registry = new IsvEventProcessorRegistry(newSet(theSoleProcessorInTheRegistry));
	}

	@Test
	public void whenProcessorForThisEventExistsItIsReturned() {
		when(theSoleProcessorInTheRegistry.supports(EventType.SUBSCRIPTION_ORDER)).thenReturn(true);

		IsvEventProcessor processorMatch = registry.get(EventType.SUBSCRIPTION_ORDER);

		assertThat(processorMatch).isEqualTo(theSoleProcessorInTheRegistry);
	}

	@Test
	public void whenGetIsCalledAndNoProcessorsSupportTheTenantIsvServiceExceptionIsThrown() {
		when(theSoleProcessorInTheRegistry.supports(EventType.SUBSCRIPTION_ORDER)).thenReturn(false);

		assertThatThrownBy(() -> registry.get(EventType.SUBSCRIPTION_ORDER)).isInstanceOf(IsvServiceException.class);
	}
}
