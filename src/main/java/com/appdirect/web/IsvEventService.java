package com.appdirect.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Supplier;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpHost;
import org.apache.http.client.utils.URIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appdirect.isv.IsvSpecificMarketplaceCredentials;
import com.appdirect.isv.IsvSpecificMarketplaceCredentialsSupplier;
import com.appdirect.isv.api.model.vo.APIResult;
import com.appdirect.isv.api.model.vo.ErrorCode;
import com.appdirect.isv.api.model.vo.EventFlag;
import com.appdirect.isv.api.model.vo.EventInfo;
import com.appdirect.isv.exception.IsvServiceException;
import com.appdirect.isv.service.processor.IsvEventProcessor;
import com.appdirect.isv.service.processor.IsvEventProcessorRegistry;

@Slf4j
@Service
@NoArgsConstructor
public class IsvEventService {
    private IsvEventFetcher isvEventFetcher;
    private IsvEventProcessorRegistry eventProcessorRegistry;
    private Supplier<IsvSpecificMarketplaceCredentials> credentialsSupplier;

    @Autowired
    public IsvEventService(IsvEventFetcher isvEventFetcher,
                           IsvEventProcessorRegistry eventProcessorRegistry,
                           IsvSpecificMarketplaceCredentialsSupplier credentialsSupplier) {
        this.isvEventFetcher = isvEventFetcher;
        this.eventProcessorRegistry = eventProcessorRegistry;
        this.credentialsSupplier = credentialsSupplier;
    }

    public APIResult processEvent(String url, String tenantId) {
        log.debug("processing event for url = {}, tenantId = {}", url, tenantId);
        try {
            String baseUrl = extractBaseMarketplaceUrl(url);
            EventInfo event = fetchEvent(url, tenantId);
            if (event.getFlag() == EventFlag.STATELESS) {
                return new APIResult(true, "success response to stateless event.");
            }
            return process(event, baseUrl, tenantId);
        } catch (IsvServiceException e) {
            // this is a business error bubble up it is handled elsewhere.
            log.error("Service returned an error for url = {}, tenant = {}, result = {}", url, tenantId, e.getResult());
            throw e;
        } catch (RuntimeException e) {
            log.error("Exception while attempting to process an event. url = {}, tenant = {}", url, tenantId, e);
            throw new IsvServiceException(ErrorCode.UNKNOWN_ERROR, String.format("Failed to process event. url = %s, tenant = %s", url, tenantId));
        }
    }

    public EventInfo fetchEvent(String url, String tenantId) {
        IsvSpecificMarketplaceCredentials credentials = credentialsSupplier.get();
        EventInfo event = isvEventFetcher.fetchEvent(url, credentials.getIsvKey(), credentials.getIsvSecret());
        log.debug("Successfully retrieved event: {}", event);
        return event;
    }

    public APIResult process(EventInfo event, String baseMarketplaceUrl, String tenantId) {
        log.debug("Processing event = {}, for tenantId = {}", event, tenantId);
        IsvSpecificMarketplaceCredentials credentials = credentialsSupplier.get();

        IsvEventProcessor processor = eventProcessorRegistry.get(null, event.getType()); // TODO: remove tenant from processor

        return processor.process(event, baseMarketplaceUrl, null); // TODO: remove tenant from processor
    }

    protected String extractBaseMarketplaceUrl(String eventUrl) {
        try {
            HttpHost httpHost = URIUtils.extractHost(new URI(eventUrl));
            return httpHost.toURI();
        } catch (URISyntaxException e) {
            log.error("Cannot parse event url", e);
            throw new IsvServiceException(String.format("Cannot parse event url = %s", eventUrl));
        }
    }
}
