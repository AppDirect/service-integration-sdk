package com.appdirect.isv.service.consumer;

import com.appdirect.isv.api.model.vo.EventInfo;

public interface IsvEventConsumer {
	/**
	 * Consumes the ISV integration event from AppDirect.
	 *
	 */
	EventInfo consumeToken(String eventUrl, String key, String secret);
}
