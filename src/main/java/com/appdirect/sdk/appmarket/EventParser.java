package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.EventInfo;
/**
 * SDK internal
 * @param <T>
 */
@FunctionalInterface
interface EventParser<T>{
	
	T parse(EventInfo e);
}
