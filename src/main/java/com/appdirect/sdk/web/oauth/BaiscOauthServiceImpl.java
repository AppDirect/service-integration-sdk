package com.appdirect.sdk.web.oauth;

import javax.servlet.Filter;

public class BaiscOauthServiceImpl implements BaiscOauthService {
	/**
	 * Returns the Basic Filter
	 *
	 * @return the Filter to authorize incoming requests
	 */
	private final BaiscOauthSupplier baiscOauthSupplier ;

	public BaiscOauthServiceImpl(BaiscOauthSupplier baiscOauthSupplier) {
		this.baiscOauthSupplier = baiscOauthSupplier;
	}

	@Override
	public Filter getBasicFilter() {
		return baiscOauthSupplier.getBasicFilter();
	}
}
