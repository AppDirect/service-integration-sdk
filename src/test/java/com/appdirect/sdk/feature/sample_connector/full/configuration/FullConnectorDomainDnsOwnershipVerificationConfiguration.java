package com.appdirect.sdk.feature.sample_connector.full.configuration;

import static java.util.stream.Collectors.toSet;

import java.util.Set;
import java.util.stream.Stream;

import com.appdirect.sdk.appmarket.domain.DnsOwnershipVerificationRecords;
import com.appdirect.sdk.appmarket.domain.DomainAdditionHandler;
import com.appdirect.sdk.appmarket.domain.DomainDnsOwnershipVerificationConfiguration;
import com.appdirect.sdk.appmarket.domain.DomainDnsVerificationInfoHandler;
import com.appdirect.sdk.appmarket.domain.DomainOwnershipVerificationHandler;
import com.appdirect.sdk.appmarket.domain.DomainVerificationNotificationClient;
import com.appdirect.sdk.appmarket.domain.MxDnsRecord;
import com.appdirect.sdk.appmarket.domain.TxtDnsRecord;

public class FullConnectorDomainDnsOwnershipVerificationConfiguration extends DomainDnsOwnershipVerificationConfiguration {

	@Override
	public DomainOwnershipVerificationHandler domainOwnershipVerificationHandler(DomainVerificationNotificationClient domainVerificationNotificationClient) {
		return (customerId, domain, callbackUrl, key) -> domainVerificationNotificationClient.resolveDomainVerification(callbackUrl, key, true);
	}

	@Override
	public DomainAdditionHandler domainAdditionHandler() {
		return (customerId, domain) -> {};
	}

	@Override
	public DomainDnsVerificationInfoHandler domainDnsVerificationInfoHandler() {
		return (customerId, domain) -> new DnsOwnershipVerificationRecords(generateTestTxtRecords(customerId, domain), generateTestMxDnsRecords());
	}

	private Set<MxDnsRecord> generateTestMxDnsRecords() {
		return Stream.of(new MxDnsRecord("@",
				3600,
				1,
				"abc.example.com."))
				.collect(toSet());
	}

	private Set<TxtDnsRecord> generateTestTxtRecords(String customerId, String domain) {
		return Stream.of(new TxtDnsRecord(
				"@",
				3600,
				"key1=value1"))
				.collect(toSet());
	}
}
