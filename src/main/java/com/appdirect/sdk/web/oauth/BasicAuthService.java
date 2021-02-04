package com.appdirect.sdk.web.oauth;

import javax.servlet.Filter;

public interface BasicAuthService {
	/**
	 * Returns the Basic Filter
	 *
	 * @return the Filter to authorize incoming requests
	 */
	Filter getBasicFilter();
}
