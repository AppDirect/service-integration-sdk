package com.appdirect.sdk.web.oauth;

import javax.servlet.Filter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		log.info("Receiving api call to doFilter");
		return baiscAuthSupplier.getBasicAuthFilter();
	}
}
