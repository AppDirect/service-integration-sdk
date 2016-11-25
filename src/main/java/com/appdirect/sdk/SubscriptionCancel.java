package com.appdirect.sdk;

import lombok.Value;

@Value
public class SubscriptionCancel {
	private final String consumerKeyUsedByTheRequest;
	private final String accountIdentifier;
}
