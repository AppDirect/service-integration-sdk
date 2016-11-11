package com.appdirect.sdk.appmarket;

import java.util.Optional;

/**
 * Represents the credentials needed to authenticate with the AppMarket.
 */
public class DeveloperSpecificAppmarketCredentials {
	private final Credentials mainProductCredentials;
	private final Credentials addonCredentials;

	public DeveloperSpecificAppmarketCredentials(Credentials mainProductCredentials) {
		this(mainProductCredentials, null);
	}

	public DeveloperSpecificAppmarketCredentials(Credentials mainProductCredentials, Credentials addonCredentials) {
		this.mainProductCredentials = mainProductCredentials;
		this.addonCredentials = addonCredentials;
	}

	/**
	 * Gets the credentials for the main product configured on the appmarket. This is never <code>null</code>.
	 *
	 * @return the credentials for the main appmarket product.
	 */
	public Credentials getMainProductCredentials() {
		return mainProductCredentials;
	}

	/**
	 * Gets the credentials for the addon product configured on the appmarket. This is optional.
	 *
	 * @return the credentials for the addon product. Can be <code>empty()</code>.
	 */
	public Optional<Credentials> getAddonCredentials() {
		return Optional.ofNullable(addonCredentials);
	}

}
