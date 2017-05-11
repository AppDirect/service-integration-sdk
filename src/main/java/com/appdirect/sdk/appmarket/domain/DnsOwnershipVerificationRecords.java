package com.appdirect.sdk.appmarket.domain;

import java.util.Set;

import lombok.Value;

@Value
public class DnsOwnershipVerificationRecords {
	Set<TxtDnsRecord> txt;
	Set<MxDnsRecord> mx;
}
