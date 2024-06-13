package com.appdirect.sdk.web.oauth.spring.oauth1;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/** @deprecated */
@Deprecated
public class OAuthAuthenticationDetails extends WebAuthenticationDetails {
    private final ConsumerDetails consumerDetails;

    public OAuthAuthenticationDetails(HttpServletRequest request, ConsumerDetails consumerDetails) {
        super(request);
        this.consumerDetails = consumerDetails;
    }

    public ConsumerDetails getConsumerDetails() {
        return this.consumerDetails;
    }
}
