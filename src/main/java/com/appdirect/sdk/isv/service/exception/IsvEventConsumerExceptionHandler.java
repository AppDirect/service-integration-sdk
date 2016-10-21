package com.appdirect.sdk.isv.service.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
public class IsvEventConsumerExceptionHandler extends AbstractMarketplaceExceptionHandler {
	public IsvEventConsumerExceptionHandler() {
		super("fetch event", log);
	}
}
