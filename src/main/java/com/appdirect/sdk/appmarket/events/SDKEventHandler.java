package com.appdirect.sdk.appmarket.events;

import java.util.Map;

/**
 * SDK-internal: meant for handling raw AppMarket.
 */
@FunctionalInterface
interface SDKEventHandler {
	APIResult handle(String consumerKeyUsedByTheRequest, EventInfo event, Map<String, String[]> queryParams);
}
