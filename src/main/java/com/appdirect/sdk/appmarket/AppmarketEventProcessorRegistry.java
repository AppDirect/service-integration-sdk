package com.appdirect.sdk.appmarket;

import java.util.Optional;
import java.util.Set;

import com.appdirect.sdk.appmarket.api.EventType;
import com.appdirect.sdk.exception.DeveloperServiceException;

public class AppmarketEventProcessorRegistry {
    private final Set<AppmarketEventProcessor> appmarketEventProcessors;

    public AppmarketEventProcessorRegistry(Set<AppmarketEventProcessor> appmarketEventProcessors) {
        this.appmarketEventProcessors = appmarketEventProcessors;
    }

    private Optional<AppmarketEventProcessor> find(EventType eventType) {
        return appmarketEventProcessors.stream()
            .filter(p -> p.supports(eventType))
            .findFirst();
    }

    public AppmarketEventProcessor get(EventType eventType) {
        return find(eventType).orElseThrow(() ->
            new DeveloperServiceException(String.format("EventType = %s is not supported.", eventType.toString()))
        );
    }
}
