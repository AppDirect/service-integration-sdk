package com.appdirect.sdk.appmarket.alt;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventInfo;

@FunctionalInterface
public interface SDKEventHandler<T> {
	APIResult handle(EventInfo event);
}
