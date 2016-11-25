package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.APIResult;
import com.appdirect.sdk.SubscriptionCancel;
import com.appdirect.sdk.SubscriptionOrder;

/**
 * This is the interface you need to implement to support handling appmarket events
 * in your connector. Start with the {@link SubscriptionOrder} &amp;
 * {@link SubscriptionCancel} events.
 *
 * @param <T> the type of the event this handler supports.
 */
@FunctionalInterface
public interface AppmarketEventHandler<T> {
	APIResult handle(T event);
}
