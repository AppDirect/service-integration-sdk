package com.appdirect.sdk.appmarket.events;

/**
 * Denotes the secondary type of an AppMarket event with {@link EventType#SUBSCRIPTION_NOTICE}.
 * SDK internal, not meant to be consumed by users of the library.
 */
enum NoticeType {
	REACTIVATED,
	DEACTIVATED,
	CLOSED,
	UPCOMING_INVOICE
}
