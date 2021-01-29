package com.appdirect.sdk.web.oauth;

import javax.servlet.Filter;

public class BaiscOauthSupplierImpl implements  BaiscOauthSupplier{

	private final Filter basicFilter;

	public BaiscOauthSupplierImpl(Filter oAuth2Filter) {
		this.basicFilter = oAuth2Filter;
	}

	@Override
	public Filter getBasicFilter() {
		return basicFilter;
	}
}
