package com.appdirect.sdk.isv.service.processor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appdirect.sdk.isv.api.model.type.EventType;
import com.appdirect.sdk.isv.exception.IsvServiceException;

@Component
@Slf4j
public class IsvEventProcessorRegistry {
    @Autowired
    private Set<IsvEventProcessor> isvEventProcessors = new HashSet<>();

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
