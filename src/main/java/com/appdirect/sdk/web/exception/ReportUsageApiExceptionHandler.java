/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appdirect.sdk.web.exception;

import static com.appdirect.sdk.appmarket.events.ErrorCode.CONFIGURATION_ERROR;
import static com.appdirect.sdk.appmarket.events.ErrorCode.UNKNOWN_ERROR;
import static com.appdirect.sdk.appmarket.events.ErrorCode.USER_NOT_FOUND;
import static java.lang.String.format;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.appdirect.sdk.exception.ReportUsageException;

@Slf4j
public class ReportUsageApiExceptionHandler implements ResponseErrorHandler {

	private static final String MESSAGE_NOT_FOUND = "User not found.";
	private static final String MESSAGE_BAD_REQUEST = "Usage missing data.";

	@Override
	public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
		return !clientHttpResponse.getStatusCode().is2xxSuccessful();
	}

	@Override
	public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
		log.error("Response error: statusCode={} statusText={}", clientHttpResponse.getStatusCode(), clientHttpResponse.getStatusText());
		switch (clientHttpResponse.getStatusCode().value()) {
			case 404:
				throw new ReportUsageException(USER_NOT_FOUND, errorMessage(MESSAGE_NOT_FOUND));
			case 400:
				throw new ReportUsageException(CONFIGURATION_ERROR, errorMessage(MESSAGE_BAD_REQUEST));
			default:
				throw new ReportUsageException(UNKNOWN_ERROR, errorMessage(clientHttpResponse.getStatusText()));
		}
	}

	private String errorMessage(String status) throws IOException {
		return format("Failed to report usage: %s", status);
	}
}
