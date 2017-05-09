package com.appdirect.sdk.appmarket.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainDnsVerification {
	@Bean
	public DomainDnVerificationInfoHandler defaultVerificationHandler() {
		return new DefaultDomainDnVerificationInfoHandlerImpl();
	}

	@Bean
	public DomainDnsVerificationInfoController domainDnsVerificationInfoController(DomainDnVerificationInfoHandler verificationInfoHandler) {
		return new DomainDnsVerificationInfoController(verificationInfoHandler);
	}
}
