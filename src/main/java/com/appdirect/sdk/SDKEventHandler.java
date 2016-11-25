package com.appdirect.sdk;

@FunctionalInterface
interface SDKEventHandler {
	APIResult handle(String consumerKeyUsedByTheRequest, EventInfo event);
}
