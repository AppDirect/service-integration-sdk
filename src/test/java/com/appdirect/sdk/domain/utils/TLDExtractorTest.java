package com.appdirect.sdk.domain.utils;

import static com.appdirect.sdk.domain.utils.TLDExtractor.extractTLDFromDomain;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.testng.annotations.Test;

public class TLDExtractorTest {
    @Test
    public void extractTLDFromDomain_withValidDomains_shouldReturnTLD() throws Exception {
        assertThat(extractTLDFromDomain("iamabanana.ca")).isEqualTo("ca");
        assertThat(extractTLDFromDomain("iamabanana.com")).isEqualTo("com");
        assertThat(extractTLDFromDomain("iamabanana.gouv")).isEqualTo("gouv");
        assertThat(extractTLDFromDomain("iamabanana.qc.ca")).isEqualTo("qc.ca");
    }

    @Test
    public void extractTLDFromDomain_withInvalidDomain_shouldThrowException() throws Exception {
        verifyExceptionThrownForDomain(null);
        verifyExceptionThrownForDomain("");
    }

    private void verifyExceptionThrownForDomain(String domain) {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> extractTLDFromDomain(domain))
            .withMessage("domain should not be null or empty");
    }
}
