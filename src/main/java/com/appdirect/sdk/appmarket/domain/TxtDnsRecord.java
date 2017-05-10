package com.appdirect.sdk.appmarket.domain;

import lombok.Value;

/**
 * Represents a TXT DNS record.
 */
@Value
public final class TxtDnsRecord {
	String name;
	int ttl;
	String text;
}
