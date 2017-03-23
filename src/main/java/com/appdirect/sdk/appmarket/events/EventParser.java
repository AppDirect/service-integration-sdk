package com.appdirect.sdk.appmarket.events;

/**
 * SDK internal - parses an EventInfo and returns a rich event of type T
 *
 * @param <T> the type of the rich event returned by this parser
 */
@FunctionalInterface
interface EventParser<T> {
	/**
	 * Parses a raw {@link EventInfo} instance received from the AppMarket into the corresponding
	 * rich event type (that is visible to clients of the SDK)
	 *
	 * @param eventInfo    representation of the raw event fetched from the AppMarket
	 * @param eventContext contextual information about the event
	 * @return The rich (client visible) event corresponding to the {@link EventInfo} input parameter
	 */
	T parse(EventInfo eventInfo, EventHandlingContext eventContext);
}
