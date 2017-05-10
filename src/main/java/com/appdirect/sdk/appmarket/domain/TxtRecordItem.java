package com.appdirect.sdk.appmarket.domain;

import lombok.Value;

/**
 * Represents a single key-value pair stored within the text part of a TXT dns record.
 */
@Value
public class TxtRecordItem {
	String key;
	String value;
}
