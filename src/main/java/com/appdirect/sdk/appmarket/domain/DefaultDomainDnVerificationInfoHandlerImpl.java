package com.appdirect.sdk.appmarket.domain;

import com.appdirect.sdk.web.exception.NotFoundException;

public class DefaultDomainDnVerificationInfoHandlerImpl implements DomainDnVerificationInfoHandler {
	@Override
	public TXTDnsRecord readOwnershipVerificationRecord(String customerId, String domain) {
		throw new NotFoundException();
	}
}
