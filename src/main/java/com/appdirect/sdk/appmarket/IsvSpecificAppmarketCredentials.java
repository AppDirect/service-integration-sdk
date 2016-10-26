package com.appdirect.sdk.appmarket;

public class IsvSpecificAppmarketCredentials {
    private final String isvKey;
    private final String isvSecret;

    public IsvSpecificAppmarketCredentials(String isvKey, String isvSecret) {
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
