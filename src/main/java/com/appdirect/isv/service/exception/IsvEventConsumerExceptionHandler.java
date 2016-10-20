package com.appdirect.isv.service.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IsvEventConsumerExceptionHandler extends AbstractMarketplaceExceptionHandler {
	public IsvEventConsumerExceptionHandler() {
		super("fetch event", log);
	}
}
