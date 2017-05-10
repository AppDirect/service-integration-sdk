package com.appdirect.sdk.appmarket.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provides the dependencies necessary for a connector for domain linking functionality.
 * This configuration has to be explicitly imported by the SDK client, the same as the 
 * {@link com.appdirect.sdk.ConnectorSdkConfiguration} class.
 */
@Configuration
public class DomainDnsOwnershipVerificationConfiguration {

	@Bean
	public DomainDnsOwnershipVerificationInfoController domainDnsVerificationInfoController(DomainDnVerificationInfoHandler handler) {
		return new DomainDnsOwnershipVerificationInfoController(handler);
	}
}
