package com.appdirect.sdk.isv.service.processor;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appdirect.sdk.isv.api.model.type.EventType;
import com.appdirect.sdk.isv.exception.IsvServiceException;

@Component
public class IsvEventProcessorRegistry {
    private final Set<IsvEventProcessor> isvEventProcessors;

    @Autowired
    public IsvEventProcessorRegistry(Set<IsvEventProcessor> isvEventProcessors) {
        this.isvEventProcessors = isvEventProcessors;
    }

    private Optional<IsvEventProcessor> find(EventType eventType) {
        return isvEventProcessors.stream()
            .filter(p -> p.supports(eventType))
            .findFirst();
    }

    public IsvEventProcessor get(EventType eventType) {
        return find(eventType).orElseThrow(() ->
            new IsvServiceException(String.format("EventType = %s is not supported.", eventType.toString()))
        );
    }
}
