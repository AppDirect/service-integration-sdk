package com.appdirect.sdk.appmarket.domain;

import java.util.Set;

import lombok.Value;

@Value
public final class TXTDnsRecord {
	String name;
	int ttl;
	String clazz = "IN";
	String rr;
	Set<TxtRecordItem> text;
}
