package com.appdirect.isv.service.processor;

import java.util.Optional;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appdirect.isv.api.model.type.EventType;
import com.appdirect.isv.exception.IsvServiceException;
import com.appdirect.tenant.model.Tenant;

@Component
@Slf4j
public class IsvEventProcessorRegistry {
    private Set<IsvEventProcessor> isvEventProcessors;

    @Autowired
    public IsvEventProcessorRegistry(Set<IsvEventProcessor> isvEventProcessors) {
        this.isvEventProcessors = isvEventProcessors;
    }

    public Optional<IsvEventProcessor> find(Tenant tenant, EventType eventType) {
        return isvEventProcessors.stream()
                .filter(p -> p.supports(tenant, eventType))
                .findFirst();
    }

    public IsvEventProcessor get(Tenant tenant, EventType eventType) {
        return find(tenant, eventType).orElseThrow(() ->
                new IsvServiceException(String.format("EventType = %s is not supported.", eventType.toString()))
        );
    }
}
