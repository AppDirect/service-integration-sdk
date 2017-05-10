package com.appdirect.sdk.appmarket.domain;

/**
 * SDK clients must implement this interface in their {@link org.springframework.context.ApplicationContext}
 * if they are using the {@link DomainDnsOwnershipVerificationConfiguration}. The implementation of this class specifies the
 * {@link TxtDnsRecord} that is returned to the monolith upon querying.
 */
public interface DomainDnVerificationInfoHandler {
	DnsOwnershipVerificationRecords readOwnershipVerificationRecords(String customerId, String domain);
}
