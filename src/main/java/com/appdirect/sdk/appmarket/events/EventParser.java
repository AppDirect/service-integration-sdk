package com.appdirect.sdk.appmarket.events;

/**
 * SDK internal - parses an EventInfo and returns a rich event of type T
 *
 * @param <T> the type of the rich event returned by this parser
 */
@FunctionalInterface
interface EventParser<T> {
	T parse(EventInfo eventInfo, EventExecutionContext eventContext);
}
