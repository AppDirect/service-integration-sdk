package com.appdirect.sdk.web.exception;

import static com.appdirect.sdk.ErrorCode.NOT_FOUND;
import static java.lang.String.format;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.appdirect.sdk.exception.DeveloperServiceException;

@Slf4j
public class AppmarketEventConsumerExceptionHandler implements ResponseErrorHandler {
	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		log.error("Response error: code={} text={}", response.getStatusCode(), response.getStatusText());
		if (HttpStatus.NOT_FOUND == response.getStatusCode()) {
			throw new DeveloperServiceException(NOT_FOUND, errorMessage(response.getStatusText()));
		} else {
			throw new DeveloperServiceException(errorMessage(response.getStatusText()));
		}
	}

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return !response.getStatusCode().is2xxSuccessful();
	}

	private String errorMessage(String status) throws IOException {
		return format("Failed to fetch event: %s", status);
	}
}
