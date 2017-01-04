package com.appdirect.sdk.appmarket.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Representation of the JSON payload of an event received from the AppMarket.
 * SDK internal, a user of the SDK should never interact with those directly.
 */
@Getter
@ToString
@AllArgsConstructor
@Builder
class EventInfo {
	private EventType type;
	private MarketInfo marketplace;
	private String applicationUuid;
	private EventFlag flag;
	private UserInfo creator;
	private EventPayload payload;
	private String returnUrl;
	@Setter private String id;
}
