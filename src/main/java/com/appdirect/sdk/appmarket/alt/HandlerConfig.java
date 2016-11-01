package com.appdirect.sdk.appmarket.alt;

import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.appmarket.alt.events.EventA;

@Configuration
public class HandlerConfig {
	
	public EventParser<EventA> eventAEventParser() {
		return e -> new EventA();
	}

	public SDKEventHandler eventAHandle(DeveloperEventHandler<EventA> devHandler) {
		return new SDKEventHandler<>(eventAEventParser(), devHandler);
	}
}
