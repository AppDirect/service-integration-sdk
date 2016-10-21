package com.appdirect.web;

import java.util.Set;
import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.appdirect.web.oauth.IsvSpecificMarketplaceCredentialsConsumerDetailsService;
import com.appdirect.isv.IsvSpecificMarketplaceCredentials;
import com.appdirect.isv.service.processor.IsvEventProcessor;
import com.appdirect.isv.service.processor.IsvEventProcessorRegistry;
import com.appdirect.tenant.model.vo.TenantBean;

public class ConnectorStarter {

    private final Supplier<IsvSpecificMarketplaceCredentials> credentialsSupplier;
    private final Set<IsvEventProcessor<? extends TenantBean>> eventProcessors;

    public ConnectorStarter(Supplier<IsvSpecificMarketplaceCredentials> credentialsSupplier,
                            Set<IsvEventProcessor<? extends TenantBean>> eventProcessors) {
        this.credentialsSupplier = credentialsSupplier;
        this.eventProcessors = eventProcessors;
    }

    public void start(String... args) {
        ConfigurableApplicationContext context = SpringApplication.run(ConnectorApplication.class, args);

        setCredentialsSupplier(context);

        IsvEventProcessorRegistry registry = context.getBean(IsvEventProcessorRegistry.class);
        registry.setIsvEventProcessors(eventProcessors);
    }

    private void setCredentialsSupplier(ConfigurableApplicationContext context) {
        IsvEventService eventService = context.getBean(IsvEventService.class);
        eventService.setCredentialsSupplier(credentialsSupplier);

        IsvSpecificMarketplaceCredentialsConsumerDetailsService consumerDetailsService = context.getBean(IsvSpecificMarketplaceCredentialsConsumerDetailsService.class);
        consumerDetailsService.setCredentialsSupplier(credentialsSupplier);
    }
}
