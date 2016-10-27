package com.appdirect.sdk.web.oauth;

import java.util.function.Supplier;

import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;

import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentials;

public class DeveloperSpecificAppmarketCredentialsConsumerDetailsService implements ConsumerDetailsService {
    private final Supplier<DeveloperSpecificAppmarketCredentials> credentialsSupplier;

    public DeveloperSpecificAppmarketCredentialsConsumerDetailsService(Supplier<DeveloperSpecificAppmarketCredentials> credentialsSupplier) {
        this.credentialsSupplier = credentialsSupplier;
    }

    @Override
    public ConsumerDetails loadConsumerByConsumerKey(String consumerKey) {
        DeveloperSpecificAppmarketCredentials credentials = credentialsSupplier.get();

        return consumerDetailsFrom(credentials);
    }

    private ConsumerDetails consumerDetailsFrom(DeveloperSpecificAppmarketCredentials credentials) {
        return new ConnectorConsumerDetails(credentials.getDeveloperKey(), credentials.getDeveloperSecret());
    }
}
