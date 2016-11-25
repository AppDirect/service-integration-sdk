package com.appdirect.sdk.appmarket.api;

@FunctionalInterface
interface SDKEventHandler {
	APIResult handle(String consumerKeyUsedByTheRequest, EventInfo event);
}
