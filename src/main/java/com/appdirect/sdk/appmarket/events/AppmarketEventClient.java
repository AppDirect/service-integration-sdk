/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.utils.EventIdExtractor.extractId;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.client.RestTemplate;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.saml.ServiceProviderInformation;
import com.appdirect.sdk.web.oauth.OAuth2ClientDetailsService;
import com.appdirect.sdk.web.oauth.RestTemplateFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class defines method for performing HTTP requests against an AppMarket instance
 */
@Slf4j
public class AppmarketEventClient {
    private final RestTemplateFactory restTemplateFactory;
    private final DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;
    private final ObjectMapper jsonMapper;

    AppmarketEventClient(RestTemplateFactory restTemplateFactory, DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier, ObjectMapper jsonMapper) {
        this.restTemplateFactory = restTemplateFactory;
        this.credentialsSupplier = credentialsSupplier;
        this.jsonMapper = jsonMapper;
    }

    /**
     * Perform "signed fetch" in order to retrieve the payload of an event sent to the connector from the AppMarket
     *
     * @param url         from which we can fetch the event payload
     * @param credentials the credentials used to sign the request
     * @return an {@link EventInfo} instance representing the retrieved payload
     */
    EventInfo fetchEvent(String url, Credentials credentials) {
        log.debug("Consuming event from url={}", url);
        final RestTemplate restTemplate = restTemplateFactory
                .getOAuthRestTemplate(credentials.developerKey, credentials.developerSecret);
        return execute(url, restTemplate);
    }

    EventInfo fetchEvents(String url, OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails) {
        log.debug("Consuming event from url={}", url);
        final RestTemplate restTemplate = restTemplateFactory.getOAuth2RestTemplate(oAuth2ProtectedResourceDetails);
        return execute(url, restTemplate);
    }

    private EventInfo execute(String url, RestTemplate restTemplate) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(singletonList(APPLICATION_JSON));
        final HttpEntity<String> requestEntity = new HttpEntity<>("", requestHeaders);
        EventInfo fetchedEvent = restTemplate
                .exchange(url, GET, requestEntity, EventInfo.class)
                .getBody();

        fetchedEvent.setId(extractId(url));
        return fetchedEvent;
    }

    /**
     * Send an "event resolved" notification for an asynchronous event. It serves to notify the
     * AppMarket that the processing of a given event by the connector has been completed
     *
     * @param baseAppmarketUrl host on which the marketplace is running
     * @param eventToken       the id of the event we would like to resolve
     * @param result           represents the event processing result sent to the AppMarket. It would indicate if the event
     *                         processing has been successful or not.
     * @param key              the client key used to sign the resolve request
     */
    @Deprecated
    @SneakyThrows
    public void resolve(String baseAppmarketUrl, String eventToken, APIResult result, String key) {
        String url = eventResolutionEndpoint(baseAppmarketUrl, eventToken);
        String secret = credentialsSupplier.getConsumerCredentials(key).developerSecret;

        final RestTemplate restTemplate = restTemplateFactory.getOAuthRestTemplate(key, secret);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(APPLICATION_JSON);

        final HttpEntity<String> requestEntity = new HttpEntity<>(jsonMapper.writeValueAsString(result), requestHeaders);

        restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        log.info("Resolved event with eventToken={} with apiResult={}", eventToken, result);
    }


    @SneakyThrows
    public void resolve(String baseAppmarketUrl, String eventToken, APIResult result, OAuth2ProtectedResourceDetails oAuth2ResourceDetails) {
        log.info("received request for");
        String url = eventResolutionEndpoint(baseAppmarketUrl, eventToken);

        final RestTemplate restTemplate = restTemplateFactory.getOAuth2RestTemplate(oAuth2ResourceDetails);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(APPLICATION_JSON);

        final HttpEntity<String> requestEntity = new HttpEntity<>(jsonMapper.writeValueAsString(result), requestHeaders);

        restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        log.info("Resolved event with eventToken={} with apiResult={}", eventToken, result);
    }

    @Deprecated
    public ServiceProviderInformation resolveSamlIdp(String url, String key) {
        String secret = credentialsSupplier.getConsumerCredentials(key).developerSecret;
        return restTemplateFactory.getOAuthRestTemplate(key, secret).getForObject(url, ServiceProviderInformation.class);
    }

    public ServiceProviderInformation resolveSamlIdp(String url, OAuth2ProtectedResourceDetails oAuth2ResourceDetails) {
        log.info("calling Oauth2 resolveSamlIdp");
        return restTemplateFactory.getOAuth2RestTemplate(oAuth2ResourceDetails).getForObject(url, ServiceProviderInformation.class);
    }

    private String eventResolutionEndpoint(String baseAppmarketUrl, String eventToken) {
        return format("%s/api/integration/v1/events/%s/result", baseAppmarketUrl, eventToken);
    }
}
