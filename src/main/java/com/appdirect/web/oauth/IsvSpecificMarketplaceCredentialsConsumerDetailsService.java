package com.appdirect.web.oauth;

import java.util.function.Supplier;

import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.provider.BaseConsumerDetails;
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

    private BaseConsumerDetails consumerDetailsFrom(IsvSpecificMarketplaceCredentials credentials) {
        BaseConsumerDetails consumerDetails = new BaseConsumerDetails();
        consumerDetails.setConsumerKey(credentials.getIsvKey());
        consumerDetails.setSignatureSecret(new SharedConsumerSecretImpl(credentials.getIsvSecret()));
        return consumerDetails;
    }

    public void setCredentialsSupplier(Supplier<IsvSpecificMarketplaceCredentials> credentialsSupplier) {
        this.credentialsSupplier = credentialsSupplier;
    }
}
