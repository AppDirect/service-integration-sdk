package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.api.APIResult;

/**
 * This is the interface you need to implement to support handling appmarket events
 * in your connector. Start with the {@link com.appdirect.sdk.appmarket.api.SubscriptionOrder} &amp;
 * {@link com.appdirect.sdk.appmarket.api.SubscriptionCancel} events.
 *
 * @param <T> the type of the event this handler supports.
 */
@FunctionalInterface
public interface AppmarketEventHandler<T> {
	APIResult handle(T event);
}
