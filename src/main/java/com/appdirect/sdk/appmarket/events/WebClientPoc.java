package com.appdirect.sdk.appmarket.events;

import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class WebClientPoc {

    public static void main(String args[]) throws UnsupportedEncodingException {
        String url = "https%3A%2F%2Fod-fkhbegrbb.od26.appdirectondemand.com%2Fapi%2Fintegration%2Fv1%2Fevents%2Fe265d479-535a-48db-a0ff-f1f24bf3848d";
        String applicationUuid = "1ffe976c-021a-4b36-b724-47b08a62d8b6";
        ClientRegistration clientRegistrationDetails = getOAuth2ProtectedResourceDetails(applicationUuid);
        System.out.println("clientRegistrationDetails " + clientRegistrationDetails.getClientId());
        InMemoryReactiveClientRegistrationRepository clientRegistrations = new InMemoryReactiveClientRegistrationRepository(clientRegistrationDetails);
        WebClient webClient = webClient(clientRegistrations);
        EventInfo event = executeEvent(url, webClient);
        System.out.println("eventInfo" + event);
    }

    public static ClientRegistration getOAuth2ProtectedResourceDetails(String applicationUuid) {
        ClientRegistration registration = ClientRegistration
                .withRegistrationId("test")
                .tokenUri("https://od-fkhbegrbb.od26.appdirectondemand.com/oauth2/token")
                .clientId("NNsfA8N8SS")
                .clientSecret("nFrYDQVNJDp4yqmAVNIH")
                .scope("ROLE_APPLICATION")
                .authorizationGrantType(new AuthorizationGrantType("client_credentials"))
                .build();
        return registration;
    }

    public static WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {
        System.out.println("inside webclient config");
        InMemoryReactiveOAuth2AuthorizedClientService clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations);
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, clientService);
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth.setDefaultClientRegistrationId("test");
        System.out.println("oauth " + oauth);
        return WebClient.builder()
                .filter(oauth)
                .build();
    }

    private static EventInfo executeEvent(String url, WebClient webClient) throws UnsupportedEncodingException {
        System.out.println("webClient " + webClient);
        String urlString = URLDecoder.decode(url, "UTF-8");
        EventInfo fetchedEvent = webClient.get()
                .uri(urlString)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(EventInfo.class)
                .block();
        System.out.println("response is" + fetchedEvent);
        return fetchedEvent;
    }
}
