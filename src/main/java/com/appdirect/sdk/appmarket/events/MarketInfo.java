package com.appdirect.sdk.appmarket.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Contains information about the AppMarket instance from which an event originates.
 * Part of {@link EventInfo}, and is SDK internal and not meant to be directly consumed by the clients
 * of the library.
 */
@Getter
@ToString
@AllArgsConstructor
class MarketInfo {
	private String partner;
	private String baseUrl;
}
