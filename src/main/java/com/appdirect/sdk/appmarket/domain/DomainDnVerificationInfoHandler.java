package com.appdirect.sdk.appmarket.domain;

public interface DomainDnVerificationInfoHandler {
	TXTDnsRecord readOwnershipVerificationRecord(String customerId, String domain);
}
