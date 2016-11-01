package com.appdirect.sdk.appmarket.alt;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import com.appdirect.sdk.appmarket.alt.events.EventA;
import com.appdirect.sdk.appmarket.alt.events.EventB;
import com.appdirect.sdk.appmarket.alt.events.EventC;
import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventInfo;

@RequiredArgsConstructor
public class EventDispatcher {
	//NOTE: instead of having the deps explicitly, we can have a bean that is a Map<String, SDKEventHandler<?>> to store them
	//NOTE2: Here the parsing is done in the (SDK internal) SDKEventHandler, but it could just as well be done here and
	//		 have the DevelopperEventProcessor be called directly 
	private final SDKEventHandler<EventA> eventADeveloperEventProcessor;
	private final SDKEventHandler<EventB> eventBDeveloperEventProcessor;
	private final SDKEventHandler<EventC> eventCDeveloperEventProcessor;
	private final Map<String, String> saf;
	
	public APIResult dispatchAndHandle(EventInfo eventInfo) {

		switch (eventInfo.getType()) {
			case SUBSCRIPTION_CANCEL:
				return eventADeveloperEventProcessor.handle(eventInfo);
			case SUBSCRIPTION_ORDER:
				eventBDeveloperEventProcessor.handle(eventInfo);
			case SUBSCRIPTION_CHANGE:
				eventCDeveloperEventProcessor.handle(eventInfo);
			default:
				throw new RuntimeException();
		}
	}
}
