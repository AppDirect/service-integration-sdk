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
 *
 */

package com.appdirect.sdk.web.exception;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.CharEncoding;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.appdirect.sdk.appmarket.usersync.UserSyncApiResult;
import com.appdirect.sdk.exception.UserSyncException;
import com.appdirect.sdk.exception.UserSyncTooManyRequestsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

@Slf4j
public class UserSyncApiExceptionHandler implements ResponseErrorHandler {
  private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
		return !clientHttpResponse.getStatusCode().is2xxSuccessful();
	}

	@Override
	public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
		log.error("Response error: statusCode={} statusText={}", clientHttpResponse.getStatusCode(), clientHttpResponse.getStatusText());
		if (clientHttpResponse.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
			throw new UserSyncTooManyRequestsException(clientHttpResponse.getStatusText());
		}
		try {
			UserSyncApiResult userSyncResultWS = createUserSyncApiResult(clientHttpResponse);
			throw new UserSyncException(userSyncResultWS.getMessage(), userSyncResultWS);
		} catch (JsonProcessingException e) {
			String formatString = String.format("Wrong format of the error returned in UserSync Response statusCode=%s statusText=%s",	clientHttpResponse.getStatusCode(),	clientHttpResponse.getStatusText());
			log.error(formatString, e);
			throw new UserSyncException(formatString, createEmptyApiResult());
		} catch (IOException e) {
			String errorString = String.format("There was an IO error processing error statusCode=%s statusText=%s",	clientHttpResponse.getStatusCode(),	clientHttpResponse.getStatusText());
			log.error(errorString, e);
			throw new UserSyncException(errorString, createEmptyApiResult());
		}
	}

	private UserSyncApiResult createUserSyncApiResult(ClientHttpResponse clientHttpResponse) throws IOException {
		if (clientHttpResponse.getBody() == null) {
			return createEmptyApiResult();
		}
		String error = IOUtils.toString(clientHttpResponse.getBody(), CharEncoding.UTF_8);
		return objectMapper.readValue(error, UserSyncApiResult.class);
	}

	private UserSyncApiResult createEmptyApiResult() throws IOException {
		return new UserSyncApiResult("", "");
	}
}
