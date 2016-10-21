package com.appdirect.sdk.web.oauth;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth.common.signature.SharedConsumerSecret;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.common.signature.SignatureSecret;
import org.springframework.security.oauth.provider.ExtraTrustConsumerDetails;

public class ConnectorConsumerDetails implements ExtraTrustConsumerDetails {
    private static final long serialVersionUID = -6907656091309880557L;

    private final String key;
    private final SharedConsumerSecret secret;

    public ConnectorConsumerDetails(String key, String secret) {
        this.key = key;
        this.secret = new SharedConsumerSecretImpl(secret);
    }

    @Override
    public String getConsumerKey() {
        return key;
    }

    @Override
    public String getConsumerName() {
        return "";
    }

    @Override
    public SignatureSecret getSignatureSecret() {
        return secret;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isRequiredToObtainAuthenticatedToken() {
        return false;
    }
}
