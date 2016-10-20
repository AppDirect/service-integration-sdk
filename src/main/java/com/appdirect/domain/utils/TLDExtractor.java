package com.appdirect.domain.utils;

public class TLDExtractor {
    private TLDExtractor() {
    }

    public static String extractTLDFromDomain(String domain) {
        if (domain == null || domain.isEmpty()) {
            throw new IllegalArgumentException("domain should not be null or empty");
        }

        return domain.substring(domain.indexOf(".") + 1);
    }
}
