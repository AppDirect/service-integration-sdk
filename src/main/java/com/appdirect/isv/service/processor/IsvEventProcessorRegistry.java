package com.appdirect.isv.service.processor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.appdirect.isv.api.model.type.EventType;
import com.appdirect.isv.exception.IsvServiceException;
import com.appdirect.tenant.model.Tenant;
import com.appdirect.tenant.model.vo.TenantBean;

@Component
@Slf4j
public class IsvEventProcessorRegistry {
    @Setter
    private Set<IsvEventProcessor<? extends TenantBean>> isvEventProcessors = new HashSet<>();

    private Optional<IsvEventProcessor<? extends TenantBean>> find(Tenant tenant, EventType eventType) {
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
