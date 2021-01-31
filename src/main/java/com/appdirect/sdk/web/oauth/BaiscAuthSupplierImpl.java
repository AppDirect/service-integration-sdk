package com.appdirect.sdk.web.oauth;

import javax.servlet.Filter;

public class BaiscAuthSupplierImpl implements BaiscAuthSupplier {

	private final Filter basicFilter;

	public BaiscAuthSupplierImpl(Filter oAuth2Filter) {
		this.basicFilter = oAuth2Filter;
	}

	@Override
	public Filter getBasicAuthFilter() {
		return basicFilter;
	}
}
