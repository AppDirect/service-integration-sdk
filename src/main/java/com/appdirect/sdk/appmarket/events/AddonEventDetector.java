package com.appdirect.sdk.appmarket.events;

import java.util.Optional;

/**
 * Determines if an event is related to an add-on subscription or to a regular subscription.
 * This is used to dispatch the add-on events to the add-on event handlers.
 */
public class AddonEventDetector {

	/**
	 * Determines if a given event is related to an add-on subscription or not.
	 *
	 * @param rawEvent the event to check; can't be null.
	 * @return <code>true</code> if the event is related to an add-on subscription; <code>false</code> otherwise.
	 */
	public boolean eventIsRelatedToAddon(EventInfo rawEvent) {
		return Optional.ofNullable(rawEvent.getPayload()).map(EventPayload::getAccount).map(AccountInfo::getParentAccountIdentifier).isPresent();
	}
}
