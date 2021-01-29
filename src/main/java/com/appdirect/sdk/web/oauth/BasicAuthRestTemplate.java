package com.appdirect.sdk.web.oauth;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


public class BasicAuthRestTemplate extends RestTemplate {
    private final String username;
    private final String password;
    private static final String BASIC = "Basic";

    public BasicAuthRestTemplate(String username, String password) {
        super(clientHttpRequestFactory());
        this.username = username;
        this.password = password;
    }

    private static ClientHttpRequestFactory clientHttpRequestFactory() {
        return new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
    }


    @Override
    protected ClientHttpRequest createRequest(URI url, HttpMethod method) throws IOException {
        ClientHttpRequest request = super.createRequest(url, method);

        String authorization = Base64.getEncoder().encodeToString((this.username + ":" + this.password).getBytes());
        request.getHeaders().set(HttpHeaders.AUTHORIZATION, BASIC + authorization);

        return request;
    }
}
