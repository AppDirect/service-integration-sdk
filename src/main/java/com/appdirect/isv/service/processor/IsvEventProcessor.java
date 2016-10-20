package com.appdirect.isv.service.processor;

import com.appdirect.isv.api.model.type.EventType;
import com.appdirect.isv.api.model.vo.APIResult;
import com.appdirect.isv.api.model.vo.EventInfo;
import com.appdirect.tenant.model.Tenant;
import com.appdirect.tenant.model.vo.TenantBean;

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
