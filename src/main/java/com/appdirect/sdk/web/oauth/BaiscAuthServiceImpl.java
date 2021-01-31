package com.appdirect.sdk.web.oauth;

import javax.servlet.Filter;

public class BaiscAuthServiceImpl implements BaiscAuthService {
	/**
	 * Returns the Basic Filter
	 *
	 * @return the Filter to authorize incoming requests
	 */
	private final BaiscAuthSupplier baiscAuthSupplier;

	public BaiscAuthServiceImpl(BaiscAuthSupplier baiscAuthSupplier) {
		this.baiscAuthSupplier = baiscAuthSupplier;
	}

	@Override
	public Filter getBasicFilter() {
		return baiscAuthSupplier.getBasicAuthFilter();
	}
}
