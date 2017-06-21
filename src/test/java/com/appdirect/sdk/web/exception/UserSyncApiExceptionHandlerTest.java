package com.appdirect.sdk.web.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import com.appdirect.sdk.appmarket.usersync.UserSyncApiResult;
import com.appdirect.sdk.exception.UserSyncException;

public class UserSyncApiExceptionHandlerTest {
	private UserSyncApiExceptionHandler userSyncApiExceptionHandler;


	@Before
	public void setup() throws Exception {
		userSyncApiExceptionHandler = new UserSyncApiExceptionHandler();
	}

	@Test
	public void acceptedResponse_isNotError() throws Exception {
		assertThat(userSyncApiExceptionHandler.hasError(acceptedResponse())).isFalse();
	}

	@Test
	public void badRequestResponse_isAnError() throws Exception {
		assertThat(userSyncApiExceptionHandler.hasError(createResponse(HttpStatus.BAD_REQUEST))).isTrue();
	}

	@Test
	public void unauthorizedResponse_isAnError() throws Exception {
		assertThat(userSyncApiExceptionHandler.hasError(createResponse(HttpStatus.UNAUTHORIZED))).isTrue();
	}
	@Test
	public void disabledResponse_isAnError() throws Exception {
		assertThat(userSyncApiExceptionHandler.hasError(createResponse(HttpStatus.FORBIDDEN))).isTrue();
	}

	@Test
	public void tooManyRequestsResponse_isAnError() throws Exception {
		assertThat(userSyncApiExceptionHandler.hasError(createResponse(HttpStatus.TOO_MANY_REQUESTS))).isTrue();
	}

	@Test
	public void badRequestsResponse_throwsUserSyncException() throws Exception {
		try {
			userSyncApiExceptionHandler.handleError(createResponse(HttpStatus.BAD_REQUEST));
			fail("The Handler must throw a User Sync Exception while handling a bad request");
		} catch (UserSyncException e) {
			assertThat(e.getResult()).isNotNull();
			UserSyncApiResult result = e.getResult();
			assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
		}
	}

	@Test
	public void tooManyRequestsResponse_throwsUserSyncException() throws Exception {
		try {
			userSyncApiExceptionHandler.handleError(createResponse(HttpStatus.TOO_MANY_REQUESTS));
			fail("The Handler must throw a User Sync Exception while handling a bad request");
		} catch (UserSyncException e) {
			assertThat(e.getResult()).isNotNull();
			UserSyncApiResult result = e.getResult();
			assertThat(result.getStatus()).isEqualTo(HttpStatus.TOO_MANY_REQUESTS);
			assertThat(result.getCode()).isEmpty();
			assertThat(result.getMessage()).isEmpty();
		}
	}

	@Test
	public void invalidJsonResponse_throwsUserSyncException() throws Exception {
		try {
			userSyncApiExceptionHandler.handleError(createErrorResponse(HttpStatus.BAD_REQUEST));
			fail("The Handler must throw a User Sync Exception while handling a bad request");
		} catch (UserSyncException e) {
			assertThat(e.getResult()).isNotNull();
			UserSyncApiResult result = e.getResult();
			assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
			assertThat(result.getCode()).isEmpty();
			assertThat(result.getMessage()).isEmpty();
		}
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
