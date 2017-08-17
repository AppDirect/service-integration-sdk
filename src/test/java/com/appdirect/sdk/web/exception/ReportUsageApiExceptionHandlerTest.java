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
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import com.appdirect.sdk.exception.ReportUsageException;

public class ReportUsageApiExceptionHandlerTest {

	private ReportUsageApiExceptionHandler handler;

	@Before
	public void setup() throws Exception {
		handler = new ReportUsageApiExceptionHandler();
	}

	@Test
	public void okResponse_isNotError() throws Exception {
		assertThat(handler.hasError(okResponse())).isFalse();
	}

	@Test
	public void notFoundResponse_isAnError() throws Exception {
		assertThat(handler.hasError(notFoundResponse())).isTrue();
	}

	@Test
	public void badRequestResponse_isAnError() throws Exception {
		assertThat(handler.hasError(badRequestResponse())).isTrue();
	}

	@Test
	public void internalServerErrorResponse_isAnError() throws Exception {
		assertThat(handler.hasError(internalServerErrorResponse())).isTrue();
	}

	@Test
	public void notFoundResponse_throwsUserNotFoundException() throws Exception {
		assertThatThrownBy(() -> handler.handleError(notFoundResponse()))
			.isInstanceOf(ReportUsageException.class)
			.hasMessage("Failed to report usage: User not found.")
			.hasFieldOrPropertyWithValue("result.errorCode", USER_NOT_FOUND);
	}

	@Test
	public void badRequestResponse_throwsConfigurationErrorException() throws Exception {
		assertThatThrownBy(() -> handler.handleError(badRequestResponse()))
			.isInstanceOf(ReportUsageException.class)
			.hasMessage("Failed to report usage: Usage missing data.")
			.hasFieldOrPropertyWithValue("result.errorCode", CONFIGURATION_ERROR);
	}

	@Test
	public void internalServerError_throwsGenericException() throws Exception {
		assertThatThrownBy(() -> handler.handleError(internalServerErrorResponse()))
			.isInstanceOf(ReportUsageException.class)
			.hasMessage("Failed to report usage: Internal Server Error")
			.hasFieldOrPropertyWithValue("result.errorCode", UNKNOWN_ERROR);
	}

	private ClientHttpResponse notFoundResponse() throws IOException {
		return aResponse(HttpStatus.NOT_FOUND);
	}

	private ClientHttpResponse okResponse() throws IOException {
		return aResponse(HttpStatus.OK);
	}

	private ClientHttpResponse badRequestResponse() throws IOException {
		return aResponse(HttpStatus.BAD_REQUEST);
	}

	private ClientHttpResponse internalServerErrorResponse() throws IOException {
		return aResponse(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ClientHttpResponse aResponse(HttpStatus status) throws IOException {
		ClientHttpResponse response = mock(ClientHttpResponse.class);
		when(response.getStatusCode()).thenReturn(status);
		when(response.getStatusText()).thenReturn(status.getReasonPhrase());
		return response;
	}

}
