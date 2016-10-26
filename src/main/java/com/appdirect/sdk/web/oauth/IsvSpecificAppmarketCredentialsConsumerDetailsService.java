package com.appdirect.sdk.web.oauth;

import java.util.function.Supplier;

import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;

import com.appdirect.sdk.appmarket.IsvSpecificAppmarketCredentials;

public class IsvSpecificAppmarketCredentialsConsumerDetailsService implements ConsumerDetailsService {
    private final Supplier<IsvSpecificAppmarketCredentials> credentialsSupplier;

    public IsvSpecificAppmarketCredentialsConsumerDetailsService(Supplier<IsvSpecificAppmarketCredentials> credentialsSupplier) {
        this.credentialsSupplier = credentialsSupplier;
    }

    @Override
    public ConsumerDetails loadConsumerByConsumerKey(String consumerKey) {
        IsvSpecificAppmarketCredentials credentials = credentialsSupplier.get();

        return consumerDetailsFrom(credentials);
    }

    private ConsumerDetails consumerDetailsFrom(IsvSpecificAppmarketCredentials credentials) {
        return new ConnectorConsumerDetails(credentials.getIsvKey(), credentials.getIsvSecret());
    }
}
