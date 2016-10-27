package com.appdirect.sdk.appmarket;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class DeveloperSpecificAppmarketCredentials {
    private final String isvKey;
    private final String isvSecret;

    public DeveloperSpecificAppmarketCredentials(String isvKey, String isvSecret) {
        this.isvKey = isvKey;
        this.isvSecret = isvSecret;
    }

    public String getDeveloperKey() {
        return isvKey;
    }

    public String getDeveloperSecret() {
        return isvSecret;
    }
}
