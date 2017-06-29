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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import com.appdirect.sdk.exception.UserSyncException;
import com.appdirect.sdk.exception.UserSyncTooManyRequestsException;

public class UserSyncApiExceptionHandlerTest {
	private UserSyncApiExceptionHandler userSyncApiExceptionHandler;

	@Before
	public void setup() throws Exception {
		userSyncApiExceptionHandler = new UserSyncApiExceptionHandler();
	}

	@Test
	public void acceptedResponse_isNotError() throws Exception {
		//Given
		final ClientHttpResponse response = acceptedResponse();

		//When
		final Boolean hasError = userSyncApiExceptionHandler.hasError(response);

		//Then
		assertThat(hasError).isFalse();
	}

	@Test
	public void badRequestResponse_isAnError() throws Exception {
		//Given
		final ClientHttpResponse response = createResponse(HttpStatus.BAD_REQUEST);

		//When
		final Boolean hasError = userSyncApiExceptionHandler.hasError(response);

		//Then
		assertThat(hasError).isTrue();
	}

	@Test
	public void unauthorizedResponse_isAnError() throws Exception {
		//Given
		final ClientHttpResponse response = createResponse(HttpStatus.UNAUTHORIZED);

		//When
		final Boolean hasError = userSyncApiExceptionHandler.hasError(response);

		//Then
		assertThat(hasError).isTrue();
	}

	@Test
	public void disabledResponse_isAnError() throws Exception {
		//Given
		final ClientHttpResponse response = createResponse(HttpStatus.FORBIDDEN);

		//When
		final Boolean hasError = userSyncApiExceptionHandler.hasError(response);

		//Then
		assertThat(hasError).isTrue();
	}

	@Test
	public void tooManyRequestsResponse_isAnError() throws Exception {
		//Given
		final ClientHttpResponse response = createResponse(HttpStatus.TOO_MANY_REQUESTS);

		//When
		final Boolean hasError = userSyncApiExceptionHandler.hasError(response);

		//Then
		assertThat(hasError).isTrue();
	}

	@Test
	public void badRequestsResponse_throwsUserSyncException() throws Exception {
		//Given
		ClientHttpResponse clientHttpResponse = createResponse(HttpStatus.BAD_REQUEST);

		//When
		final Throwable exceptionThrown = catchThrowable(() ->
				userSyncApiExceptionHandler.handleError(clientHttpResponse)
		);

		//Then
		assertThat(exceptionThrown).isInstanceOf(UserSyncException.class);
		assertThat(((UserSyncException) exceptionThrown).getCode()).isNotNull();
		assertThat(((UserSyncException) exceptionThrown).getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
	}

	@Test
	public void tooManyRequestsResponse_throwsUserSyncException() throws Exception {
		//Given
		ClientHttpResponse clientHttpResponse = createResponse(HttpStatus.TOO_MANY_REQUESTS);

		//When
		final Throwable exceptionThrown = catchThrowable(() ->
				userSyncApiExceptionHandler.handleError(clientHttpResponse)
		);

		//Then
		assertThat(exceptionThrown).isInstanceOf(UserSyncTooManyRequestsException.class);
	}

	@Test
	public void invalidJsonResponse_throwsUserSyncException() throws Exception {
		//Given
		ClientHttpResponse clientHttpResponse = createErrorResponse(HttpStatus.BAD_REQUEST);

		//When
		final Throwable exceptionThrown = catchThrowable(() ->
				userSyncApiExceptionHandler.handleError(clientHttpResponse)
		);

		//Then
		assertThat(exceptionThrown).isInstanceOf(UserSyncException.class);
		assertThat(((UserSyncException) exceptionThrown).getCode()).isNotNull();
		assertThat(((UserSyncException) exceptionThrown).getCode()).isEqualTo("UNKNOWN_ERROR");
	}

	private ClientHttpResponse acceptedResponse() throws IOException {
		return createResponse(HttpStatus.ACCEPTED);
	}

	private ClientHttpResponse createResponse(HttpStatus status) throws IOException {
		ClientHttpResponse response = mock(ClientHttpResponse.class);
		when(response.getStatusCode()).thenReturn(status);
		when(response.getStatusText()).thenReturn(status.getReasonPhrase());
		String responseBody = String.format("{\"code\":\"%s\", \"message\":\"%s\"}", status.getReasonPhrase(), "error details");
		if (status == HttpStatus.ACCEPTED || status == HttpStatus.TOO_MANY_REQUESTS) {
			when(response.getBody()).thenReturn(null);
		} else {
			when(response.getBody()).thenReturn(IOUtils.toInputStream(responseBody, CharEncoding.UTF_8));
		}
		return response;
	}

	private ClientHttpResponse createErrorResponse(HttpStatus status) throws IOException {
		ClientHttpResponse response = mock(ClientHttpResponse.class);
		when(response.getStatusCode()).thenReturn(status);
		when(response.getStatusText()).thenReturn(status.getReasonPhrase());
		String responseBody = String.format("{\"errorCode\":\"%s\", \"message\":\"%s\"}", status.getReasonPhrase(), "error details");
		when(response.getBody()).thenReturn(IOUtils.toInputStream(responseBody, CharEncoding.UTF_8));
		return response;
	}
}
