package com.appdirect.sdk.web.oauth;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Slf4j
public class BasicAuthRestTemplate extends RestTemplate {
    private final String username;
    private final String password;

    public BasicAuthRestTemplate(String username, String password, ClientHttpRequestFactory requestFactory) {
        super(requestFactory);

        this.username = username;
        this.password = password;
    }

    @Override
    protected ClientHttpRequest createRequest(URI url, HttpMethod method) throws IOException {
        log.info("inside createRequest ");
        ClientHttpRequest request = super.createRequest(url, method);

        String authorization = Base64.getEncoder().encodeToString((this.username + ":" + this.password).getBytes());
        request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Basic " + authorization);
        log.info("authorization is {}", authorization);
        log.info("request header are {}", request.getHeaders());
        return request;
    }
}
