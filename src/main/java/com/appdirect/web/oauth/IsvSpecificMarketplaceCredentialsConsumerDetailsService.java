package com.appdirect.web.oauth;

import java.util.function.Supplier;

import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;

import com.appdirect.isv.IsvSpecificMarketplaceCredentials;

public class IsvSpecificMarketplaceCredentialsConsumerDetailsService implements ConsumerDetailsService {
    private Supplier<IsvSpecificMarketplaceCredentials> credentialsSupplier;

    @Override
    public ConsumerDetails loadConsumerByConsumerKey(String consumerKey) {
        IsvSpecificMarketplaceCredentials credentials = credentialsSupplier.get();

        return consumerDetailsFrom(credentials);
    }

    private ConsumerDetails consumerDetailsFrom(IsvSpecificMarketplaceCredentials credentials) {
        return new ConnectorConsumerDetails(credentials.getIsvKey(), credentials.getIsvSecret());
    }

    public void setCredentialsSupplier(Supplier<IsvSpecificMarketplaceCredentials> credentialsSupplier) {
        this.credentialsSupplier = credentialsSupplier;
    }
}
