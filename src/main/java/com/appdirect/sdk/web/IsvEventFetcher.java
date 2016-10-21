package com.appdirect.sdk.web;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.appdirect.sdk.isv.api.model.vo.EventInfo;
import com.appdirect.sdk.isv.service.exception.IsvEventConsumerExceptionHandler;
import com.appdirect.sdk.web.oauth.TwoLeggedOAuthClientHttpRequestFactory;

@Slf4j
@Service
public class IsvEventFetcher {
    @Autowired
    private IsvEventConsumerExceptionHandler errorHandler;

    private RestOperations restOperations(String key, String secret) {
        RestTemplate restTemplate = new RestTemplate(new TwoLeggedOAuthClientHttpRequestFactory(key, secret));
        restTemplate.setErrorHandler(errorHandler);
        return restTemplate;
    }

    public EventInfo fetchEvent(String url, String key, String secret) {
        log.debug("Consuming event from = {}", url);
        return restOperations(key, secret).getForObject(url, EventInfo.class);
    }
}
