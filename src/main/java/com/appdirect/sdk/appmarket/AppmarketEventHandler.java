package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.appmarket.events.AppmarketEventClient;
import com.appdirect.sdk.appmarket.events.SubscriptionCancel;
import com.appdirect.sdk.appmarket.events.SubscriptionOrder;

/**
 * This is the interface you need to implement to support handling appmarket events
 * in your connector. Start with the {@link SubscriptionOrder} &amp;
 * {@link SubscriptionCancel} events.
 *
 * @param <T> the type of the event this handler supports.
 */
@FunctionalInterface
public interface AppmarketEventHandler<T> {
	/**
	 * For async events (everything but <code>SUBSCRIPTION_NOTICE</code> events),
	 * returning a <code>null</code> result will not resolve the event on the appmarket's side.
	 * You will have to manually resolve the event using {@link AppmarketEventClient#resolve} at a later point in time.
	 *
	 * @param event the event received from the appmarket. Act upon this!
	 * @return the result that will be sent to the appmarket to resolve the event, or <code>null</code> if you the event is to be resolved later.
	 */
	APIResult handle(T event);
}
