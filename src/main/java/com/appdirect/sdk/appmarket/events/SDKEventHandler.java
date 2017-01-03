package com.appdirect.sdk.appmarket.events;

/**
 * SDK-internal: meant for handling raw AppMarket.
 */
@FunctionalInterface
interface SDKEventHandler {
	APIResult handle(EventInfo event, EventHandlingContext eventContext);
}
