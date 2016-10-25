package com.appdirect.sdk.marketplace.api.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.appdirect.sdk.marketplace.api.type.EventType;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventInfo implements Serializable {
	private static final long serialVersionUID = -7191601292455522677L;

	private EventType type;
	private MarketplaceInfo marketplace;
	private String applicationUuid;
	private EventFlag flag;
	private UserInfo creator;
	private EventPayload payload;
	private String returnUrl;
}
