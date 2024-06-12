package com.appdirect.sdk.security.openid;

import org.openid4java.consumer.ConsumerException;
import org.springframework.security.openid.AxFetchListFactory;
import org.springframework.security.openid.OpenID4JavaConsumer;
import org.springframework.security.openid.OpenIDConsumerException;

import jakarta.servlet.http.HttpServletRequest;

// TODO: Figure out this
public class CustomOpenIdConsumer extends OpenID4JavaConsumer {

    private final String openIdProviderUrl;

    public CustomOpenIdConsumer(final String openIdProviderUrl, final AxFetchListFactory customAxFetchFactory) throws ConsumerException {
        super(customAxFetchFactory);
        this.openIdProviderUrl = openIdProviderUrl;
    }

//    @Override
//    public String beginConsumption(HttpServletRequest httpServletRequest, String claimedIdentity, String returnToUrl, String realm) throws OpenIDConsumerException {
//        return super.beginConsumption(httpServletRequest, this.openIdProviderUrl, returnToUrl, realm);
//    }
}
