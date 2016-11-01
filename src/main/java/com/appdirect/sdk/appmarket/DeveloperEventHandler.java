package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.APIResult;

@FunctionalInterface
public interface DeveloperEventHandler<T> {
	APIResult handle(T event);
}
