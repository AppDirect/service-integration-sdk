package com.appdirect.sdk.marketplace;

import java.util.Optional;
import java.util.Set;

import com.appdirect.sdk.marketplace.api.type.EventType;
import com.appdirect.sdk.web.exception.IsvServiceException;

public class MarketplaceEventProcessorRegistry {
    private final Set<MarketplaceEventProcessor> marketplaceEventProcessors;

    public MarketplaceEventProcessorRegistry(Set<MarketplaceEventProcessor> marketplaceEventProcessors) {
        this.marketplaceEventProcessors = marketplaceEventProcessors;
    }

    private Optional<MarketplaceEventProcessor> find(EventType eventType) {
        return marketplaceEventProcessors.stream()
            .filter(p -> p.supports(eventType))
            .findFirst();
    }

    public MarketplaceEventProcessor get(EventType eventType) {
        return find(eventType).orElseThrow(() ->
            new IsvServiceException(String.format("EventType = %s is not supported.", eventType.toString()))
        );
    }
}
