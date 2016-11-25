package com.appdirect.sdk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

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
}
