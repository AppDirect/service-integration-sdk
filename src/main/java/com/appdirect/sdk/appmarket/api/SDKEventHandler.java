package com.appdirect.sdk.appmarket.api;

/**
 * SDK-internal: meant for handling raw AppMarket.
 */
@FunctionalInterface
interface SDKEventHandler {
	APIResult handle(String consumerKeyUsedByTheRequest, EventInfo event);
}
