package com.appdirect.sdk.appmarket.events;

import static java.util.Arrays.asList;

import java.util.List;

/**
 * Determines if an edition code belongs to an addon subscription or to a regular subscription.
 * This is required to dispatch the add-on events to the add-on event handlers.
 */
public class EditionCodeBasedAddonDetector {
	private final List<String> addonEditionCodes;

	/**
	 * Creates a new instance of the add-on detector.
	 *
	 * @param addonEditionCodes all of the edition codes which are related to add-on subscriptions for your product.
	 */
	public EditionCodeBasedAddonDetector(String... addonEditionCodes) {
		this.addonEditionCodes = asList(addonEditionCodes);
	}

	/**
	 * Determines if a given edition code is related to an add-on subscription or not.
	 *
	 * @param editionCode the edition code to check, can be null.
	 * @return <code>true</code> if the edition code is related to an add-on subscription; <code>false</code> otherwise.
	 */
	public boolean editionCodeIsRelatedToAddon(String editionCode) {
		return false; // TODO: Implement this!
	}
}
