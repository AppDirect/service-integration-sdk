package com.appdirect.sdk.appmarket.domain;

import lombok.Value;

/**
 * Represents a MX record
 * http://domainmx.net/mxsetup.shtml
 */
@Value
public final class MxDnsRecord {
	String destinationDomain;
	int ttlInSeconds;
	int preferenceNumber;
	String mailServerName;
}
