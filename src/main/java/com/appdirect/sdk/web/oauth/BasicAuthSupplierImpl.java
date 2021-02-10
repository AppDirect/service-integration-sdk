package com.appdirect.sdk.web.oauth;

import javax.servlet.Filter;

public class BasicAuthSupplierImpl implements BasicAuthSupplier {

	private final Filter basicFilter;

	public BasicAuthSupplierImpl(Filter oAuth2Filter) {
		this.basicFilter = oAuth2Filter;
	}

	@Override
	public Filter getBasicAuthFilter() {
		return basicFilter;
	}
}
