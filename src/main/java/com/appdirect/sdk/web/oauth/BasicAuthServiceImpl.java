package com.appdirect.sdk.web.oauth;

import javax.servlet.Filter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicAuthServiceImpl implements BasicAuthService {
	/**
	 * Returns the Basic Filter
	 *
	 * @return the Filter to authorize incoming requests
	 */
	private final BasicAuthSupplier basicAuthSupplier;

	public BasicAuthServiceImpl(BasicAuthSupplier basicAuthSupplier) {
		this.basicAuthSupplier = basicAuthSupplier;
	}

	@Override
	public Filter getBasicFilter() {
		log.info("Receiving api call to doFilter");
		return basicAuthSupplier.getBasicAuthFilter();
	}
}
