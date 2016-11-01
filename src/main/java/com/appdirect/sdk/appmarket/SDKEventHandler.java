package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventInfo;

@FunctionalInterface
interface SDKEventHandler {
	APIResult handle(EventInfo event);
}
