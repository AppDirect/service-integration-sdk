package com.appdirect.sdk.web;

import static com.appdirect.sdk.appmarket.api.ErrorCode.UNAUTHORIZED;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.exception.DeveloperServiceException;

public class DeveloperExceptionHandlerTest {

	@Test
	public void handleIsvServiceException_returnsTheApiResultInIt() throws Exception {
		DeveloperServiceException someException = new DeveloperServiceException(UNAUTHORIZED, "no no no");

		APIResult result = new DeveloperExceptionHandler().handleDeveloperServiceException(someException);

		assertThat(result).isEqualTo(unauthorized("no no no"));
	}

	private APIResult unauthorized(String message) {
		return new APIResult(UNAUTHORIZED, message);
	}
}
