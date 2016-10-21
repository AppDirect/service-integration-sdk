package com.appdirect.sdk.isv;

public class IsvSpecificMarketplaceCredentials {
    private final String isvKey;
    private final String isvSecret;

    public IsvSpecificMarketplaceCredentials(String isvKey, String isvSecret) {
        this.isvKey = isvKey;
        this.isvSecret = isvSecret;
    }

    public String getIsvKey() {
        return isvKey;
    }

    public String getIsvSecret() {
        return isvSecret;
    }
}
