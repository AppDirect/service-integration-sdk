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

import static com.appdirect.sdk.appmarket.events.ErrorCode.NOT_FOUND;
import static java.lang.String.format;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.appdirect.sdk.exception.DeveloperServiceException;

@Slf4j
public class AppmarketEventClientExceptionHandler implements ResponseErrorHandler {
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
