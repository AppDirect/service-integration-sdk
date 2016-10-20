package com.appdirect.isv.service;

import com.appdirect.isv.api.model.vo.APIResult;

public interface IsvEventService {
	/**
	 * Process an ISV event notification from AppDirect
	 *
	 * @param url
	 * @param tenantId
	 * @return
	 */
	APIResult processEvent(String url, String tenantId);
}
