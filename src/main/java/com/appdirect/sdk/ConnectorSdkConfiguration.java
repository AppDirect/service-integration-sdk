package com.appdirect.sdk;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.appdirect.sdk.isv.IsvSpecificMarketplaceCredentialsSupplier;
import com.appdirect.sdk.isv.service.exception.IsvEventConsumerExceptionHandler;
import com.appdirect.sdk.isv.service.processor.IsvEventProcessor;
import com.appdirect.sdk.isv.service.processor.IsvEventProcessorRegistry;
import com.appdirect.sdk.web.IsvController;
import com.appdirect.sdk.web.IsvEventFetcher;
import com.appdirect.sdk.web.IsvEventService;
import com.appdirect.sdk.web.config.JacksonConfiguration;
import com.appdirect.sdk.web.config.SecurityConfiguration;

@Configuration
@Import({JacksonConfiguration.class, SecurityConfiguration.class})
public class ConnectorSdkConfiguration {

    @Bean
    public IsvEventConsumerExceptionHandler isvEventConsumerExceptionHandler() {
        return new IsvEventConsumerExceptionHandler();
    }

    @Bean
    public IsvEventProcessorRegistry isvEventProcessorRegistry(Set<IsvEventProcessor> processors) {
        return new IsvEventProcessorRegistry(processors);
    }

    @Bean
    public IsvEventFetcher isvEventFetcher() {
        return new IsvEventFetcher(isvEventConsumerExceptionHandler());
    }

    @Bean
    public IsvEventService isvEventService(IsvEventProcessorRegistry isvEventProcessorRegistry,
                                           IsvSpecificMarketplaceCredentialsSupplier credentialsSupplier) {
        return new IsvEventService(isvEventFetcher(), isvEventProcessorRegistry, credentialsSupplier);
    }

    @Bean
    public IsvController isvController(IsvEventService isvEventService) {
        return new IsvController(isvEventService);
    }
}
