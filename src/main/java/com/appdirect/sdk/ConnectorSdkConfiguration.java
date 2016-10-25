package com.appdirect.sdk;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.appdirect.sdk.marketplace.IsvSpecificMarketplaceCredentialsSupplier;
import com.appdirect.sdk.marketplace.MarketplaceEventProcessor;
import com.appdirect.sdk.marketplace.MarketplaceEventProcessorRegistry;
import com.appdirect.sdk.web.IsvController;
import com.appdirect.sdk.web.IsvEventService;
import com.appdirect.sdk.web.MarketplaceEventFetcher;
import com.appdirect.sdk.web.config.JacksonConfiguration;
import com.appdirect.sdk.web.config.SecurityConfiguration;
import com.appdirect.sdk.web.exception.MarketplaceEventConsumerExceptionHandler;

@Configuration
@Import({JacksonConfiguration.class, SecurityConfiguration.class})
public class ConnectorSdkConfiguration {

    @Bean
    public MarketplaceEventConsumerExceptionHandler isvEventConsumerExceptionHandler() {
        return new MarketplaceEventConsumerExceptionHandler();
    }

    @Bean
    public MarketplaceEventProcessorRegistry isvEventProcessorRegistry(Set<MarketplaceEventProcessor> processors) {
        return new MarketplaceEventProcessorRegistry(processors);
    }

    @Bean
    public MarketplaceEventFetcher isvEventFetcher() {
        return new MarketplaceEventFetcher(isvEventConsumerExceptionHandler());
    }

    @Bean
    public IsvEventService isvEventService(MarketplaceEventProcessorRegistry marketplaceEventProcessorRegistry,
                                           IsvSpecificMarketplaceCredentialsSupplier credentialsSupplier) {
        return new IsvEventService(isvEventFetcher(), marketplaceEventProcessorRegistry, credentialsSupplier);
    }

    @Bean
    public IsvController isvController(IsvEventService isvEventService) {
        return new IsvController(isvEventService);
    }
}
