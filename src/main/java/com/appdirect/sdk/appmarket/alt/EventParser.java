package com.appdirect.sdk.appmarket.alt;

import com.appdirect.sdk.appmarket.api.EventInfo;
/**
 * SDK internal
 * @param <T>
 */
@FunctionalInterface
public interface EventParser<T>{
	
	T parse(EventInfo e);
}
