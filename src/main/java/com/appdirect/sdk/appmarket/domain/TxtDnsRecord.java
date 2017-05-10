package com.appdirect.sdk.appmarket.domain;

import java.util.Set;

import lombok.Value;

/**
 * Represents a TXT DNS record that is used to ver
 */
@Value
public final class TxtDnsRecord {
	String name;
	int ttl;
	Set<TxtRecordItem> text;
}
