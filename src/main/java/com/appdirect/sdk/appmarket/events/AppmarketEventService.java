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

import static com.appdirect.sdk.appmarket.events.ErrorCode.UNKNOWN_ERROR;
import static java.lang.String.format;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.exception.DeveloperServiceException;

@Slf4j
class AppmarketEventService {
    private final AppmarketEventClient appmarketEventClient;
    private final DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;
    private final AppmarketEventDispatcher dispatcher;

    AppmarketEventService(AppmarketEventClient appmarketEventClient,
                          DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier,
                          AppmarketEventDispatcher dispatcher) {
        this.appmarketEventClient = appmarketEventClient;
        this.credentialsSupplier = credentialsSupplier;
        this.dispatcher = dispatcher;
    }

    /**
     * Processes an event notification from the AppMarket
     *
     * @param eventUrl     the url from which we can fetch the payload of the incoming event
     * @param eventContext contextual information about the event notification
     * @return the {@link APIResult} instance representing the payload to be returned in response of the event
     * notification request
     */
    APIResult processEvent(String eventUrl, EventHandlingContext eventContext) {
        log.info("processing event for eventUrl={}", eventUrl);
        try {
            EventInfo event = fetchEvent(eventUrl, eventContext.getConsumerKeyUsedByTheRequest());
            if (event.getFlag() == EventFlag.STATELESS) {
                return APIResult.success("success response to stateless event.");
            }
            return dispatcher.dispatchAndHandle(event, eventContext);
        } catch (DeveloperServiceException e) {
            log.error("Service returned an error for eventUrl={}, result={}", eventUrl, e.getResult());
            throw e;
        } catch (RuntimeException e) {
            log.error("Exception while attempting to process an event. eventUrl={}", eventUrl, e);
            throw new DeveloperServiceException(UNKNOWN_ERROR, format("Failed to process event. eventUrl=%s | exception=%s", eventUrl, e.getMessage()));
        }
    }

    APIResult processEvent(String eventUrl, EventHandlingContext eventContext, String applicationUuid) {
        log.info("processing event for applicationUuid={} and eventUrl={}", applicationUuid, eventUrl);

        EventInfo event = fetchEventInfo(eventUrl, applicationUuid);

        if (event.getFlag() == EventFlag.STATELESS) {
            return APIResult.success("success response to stateless event.");
        }
        return executeEvent(eventUrl, event, eventContext);
    }

    private APIResult executeEvent(String eventUrl, EventInfo event, EventHandlingContext eventContext) {
        try {
            return dispatcher.dispatchAndHandle(event, eventContext);
        } catch (DeveloperServiceException e) {
            log.error("Service returned an error for eventUrl={}, result={}", eventUrl, e.getResult());
            throw e;
        } catch (RuntimeException e) {
            log.error("Exception while attempting to process an event. eventUrl={}", eventUrl, e);
            throw new DeveloperServiceException(UNKNOWN_ERROR, format("Failed to process event. eventUrl=%s | exception=%s", eventUrl, e.getMessage()));
        }
    }

    private EventInfo fetchEventInfo(String eventUrl, String applicationUuid) {
        //TODO added for testing we need to fetch ResourceDetails from connectors using applicationUuid
        OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails = getOAuth2ProtectedResourceDetails(applicationUuid);
        EventInfo event = appmarketEventClient.fetchEvents(eventUrl, oAuth2ProtectedResourceDetails);
        log.info("Successfully retrieved event={}", event);
        return event;
    }

    private OAuth2ProtectedResourceDetails getOAuth2ProtectedResourceDetails(String applicationUuid) {
        //TODO get this details from connector
        // return OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails = credentialsSupplier.getOAuth2ProtectedResourceDetails(applicationUuid);
        return createResourceDetails(applicationUuid);
    }

    //Added for local testing
    private OAuth2ProtectedResourceDetails createResourceDetails(String applicationUuid) {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setId("I33yJ9K0K1");
        details.setClientId("I33yJ9K0K1");
        details.setClientSecret("3DnUEFT1f4qY7aCPocZZKFMVptIXonWrib/BlqALtXVpavnfF0TmFBXNjPAtq37l");
        details.setAccessTokenUri("https://dummydbnode.appdirect.com/oauth2/token");
        details.setScope(Arrays.asList("ROLE_PARTNER"));
        return details;
    }

    private EventInfo fetchEvent(String url, String keyUsedToSignRequest) {
        Credentials credentials = credentialsSupplier.getConsumerCredentials(keyUsedToSignRequest);
        EventInfo event = appmarketEventClient.fetchEvent(url, credentials);
        log.info("Successfully retrieved event={}", event);
        return event;
    }
}
