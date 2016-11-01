package com.appdirect.sdk.appmarket.alt;

import com.appdirect.sdk.appmarket.api.APIResult;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@FunctionalInterface
public interface DeveloperEventHandler<T> {
	APIResult handle(T event);
}
