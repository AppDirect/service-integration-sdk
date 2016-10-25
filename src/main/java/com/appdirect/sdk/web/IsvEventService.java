package com.appdirect.sdk.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpHost;
import org.apache.http.client.utils.URIUtils;

import com.appdirect.sdk.marketplace.IsvSpecificMarketplaceCredentials;
import com.appdirect.sdk.marketplace.IsvSpecificMarketplaceCredentialsSupplier;
import com.appdirect.sdk.marketplace.MarketplaceEventProcessor;
import com.appdirect.sdk.marketplace.MarketplaceEventProcessorRegistry;
import com.appdirect.sdk.marketplace.api.vo.APIResult;
import com.appdirect.sdk.marketplace.api.vo.ErrorCode;
import com.appdirect.sdk.marketplace.api.vo.EventFlag;
import com.appdirect.sdk.marketplace.api.vo.EventInfo;
import com.appdirect.sdk.web.exception.IsvServiceException;

@Slf4j
public class IsvEventService {
    private final MarketplaceEventFetcher marketplaceEventFetcher;
    private final MarketplaceEventProcessorRegistry eventProcessorRegistry;
    private final Supplier<IsvSpecificMarketplaceCredentials> credentialsSupplier;

    public IsvEventService(MarketplaceEventFetcher marketplaceEventFetcher,
                           MarketplaceEventProcessorRegistry eventProcessorRegistry,
                           IsvSpecificMarketplaceCredentialsSupplier credentialsSupplier) {
        this.marketplaceEventFetcher = marketplaceEventFetcher;
        this.eventProcessorRegistry = eventProcessorRegistry;
        this.credentialsSupplier = credentialsSupplier;
    }

    public APIResult processEvent(String url) {
        log.info("processing event for eventUrl={}", url);
        try {
            String baseUrl = extractBaseMarketplaceUrl(url);
            EventInfo event = fetchEvent(url);
            if (event.getFlag() == EventFlag.STATELESS) {
                return new APIResult(true, "success response to stateless event.");
            }
            return process(event, baseUrl);
        } catch (IsvServiceException e) {
            // this is a business error bubble up it is handled elsewhere.
            log.error("Service returned an error for url={}, result={}", url, e.getResult());
            throw e;
        } catch (RuntimeException e) {
            log.error("Exception while attempting to process an event. eventUrl={}", url, e);
            throw new IsvServiceException(ErrorCode.UNKNOWN_ERROR, String.format("Failed to process event. url=%s", url));
        }
    }

    public EventInfo fetchEvent(String url) {
        IsvSpecificMarketplaceCredentials credentials = credentialsSupplier.get();
        EventInfo event = marketplaceEventFetcher.fetchEvent(url, credentials.getIsvKey(), credentials.getIsvSecret());
        log.info("Successfully retrieved event={}", event);
        return event;
    }

    public APIResult process(EventInfo event, String baseMarketplaceUrl) {
        log.info("Processing event={}", event);

        MarketplaceEventProcessor processor = eventProcessorRegistry.get(event.getType());

        return processor.process(event, baseMarketplaceUrl);
    }

    protected String extractBaseMarketplaceUrl(String eventUrl) {
        try {
            HttpHost httpHost = URIUtils.extractHost(new URI(eventUrl));
            return httpHost.toURI();
        } catch (URISyntaxException e) {
            log.error("Cannot parse event url", e);
            throw new IsvServiceException(String.format("Cannot parse event url=%s", eventUrl));
        }
    }
}
