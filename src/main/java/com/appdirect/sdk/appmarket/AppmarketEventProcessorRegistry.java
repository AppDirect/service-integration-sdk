package com.appdirect.sdk.appmarket;

import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.Optional;
import java.util.Set;

import com.appdirect.sdk.appmarket.api.Event;
import com.appdirect.sdk.appmarket.api.EventType;
import com.appdirect.sdk.exception.DeveloperServiceException;

public class AppmarketEventProcessorRegistry {
	private final Set<AppmarketEventProcessor<? extends Event>> appmarketEventProcessors;

	public AppmarketEventProcessorRegistry(Set<AppmarketEventProcessor<?>> appmarketEventProcessors) {
		this.appmarketEventProcessors = appmarketEventProcessors;
	}

	public <T extends Event> AppmarketEventProcessor<T> get(EventType eventType, Class<T> eventClass) {
		return this.<T>find(eventType).orElseThrow(() -> new DeveloperServiceException(format("EventType = %s is not supported.", eventType.toString())));
	}

	private <T extends Event> Optional<AppmarketEventProcessor<T>> find(EventType eventType) {
		Optional<AppmarketEventProcessor<? extends Event>> firstProcessor = appmarketEventProcessors.stream().filter(p -> p.supports(eventType)).findFirst();
		return firstProcessor.isPresent() ? of(castTypeParam(firstProcessor.get())) : empty();
	}

	@SuppressWarnings("unchecked")
	private <T extends Event> AppmarketEventProcessor<T> castTypeParam(AppmarketEventProcessor<? extends Event> processor) {
		return (AppmarketEventProcessor<T>) processor;
	}
}
