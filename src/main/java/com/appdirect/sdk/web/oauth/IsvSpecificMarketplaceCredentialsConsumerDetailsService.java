package com.appdirect.sdk.web.oauth;

import java.util.function.Supplier;

import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;

import com.appdirect.sdk.marketplace.IsvSpecificMarketplaceCredentials;

public class IsvSpecificMarketplaceCredentialsConsumerDetailsService implements ConsumerDetailsService {
    private final Supplier<IsvSpecificMarketplaceCredentials> credentialsSupplier;

    public IsvSpecificMarketplaceCredentialsConsumerDetailsService(Supplier<IsvSpecificMarketplaceCredentials> credentialsSupplier) {
        this.credentialsSupplier = credentialsSupplier;
    }

    @Override
    public ConsumerDetails loadConsumerByConsumerKey(String consumerKey) {
        IsvSpecificMarketplaceCredentials credentials = credentialsSupplier.get();

        return consumerDetailsFrom(credentials);
    }

    private ConsumerDetails consumerDetailsFrom(IsvSpecificMarketplaceCredentials credentials) {
        return new ConnectorConsumerDetails(credentials.getIsvKey(), credentials.getIsvSecret());
    }
}
