package com.appdirect.sdk.isv.service.processor;

import com.appdirect.sdk.isv.api.model.type.EventType;
import com.appdirect.sdk.isv.api.model.vo.APIResult;
import com.appdirect.sdk.isv.api.model.vo.EventInfo;
import com.appdirect.sdk.tenant.model.Tenant;
import com.appdirect.sdk.tenant.model.vo.TenantBean;

public interface IsvEventProcessor<B extends TenantBean> {
	/**
	 * Indicates if an event is supported by the processor.
	 */
	boolean supports(Tenant tenant, EventType eventType);

	/**
	 * Process Event
	 */
	APIResult process(EventInfo event, String baseMarketplaceUrl, B tenant);
}
