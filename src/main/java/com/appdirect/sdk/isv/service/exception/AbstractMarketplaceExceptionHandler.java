package com.appdirect.sdk.isv.service.exception;

import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.appdirect.sdk.isv.api.model.vo.ErrorCode;
import com.appdirect.sdk.isv.exception.IsvServiceException;

public abstract class AbstractMarketplaceExceptionHandler implements ResponseErrorHandler {
	private final String action;
	private final Logger log;

	protected AbstractMarketplaceExceptionHandler(String action, Logger log) {
		this.log = log;
		this.action = action;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		log.error("Response error: code = {} text = {}", response.getStatusCode(), response.getStatusText());
		if (HttpStatus.NOT_FOUND == response.getStatusCode()) {
			throw new IsvServiceException(ErrorCode.NOT_FOUND, String.format("Failed to %s: %s", action, response.getStatusText()));
		} else {
			throw new IsvServiceException(String.format("Failed to %s: %s", action, response.getStatusText()));
		}
	}

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return !response.getStatusCode().is2xxSuccessful();
	}
}
