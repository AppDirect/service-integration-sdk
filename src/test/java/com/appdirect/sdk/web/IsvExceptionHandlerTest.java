package com.appdirect.sdk.web;

import static com.appdirect.sdk.marketplace.api.vo.ErrorCode.UNAUTHORIZED;
import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.appdirect.sdk.exception.IsvServiceException;
import com.appdirect.sdk.marketplace.api.vo.APIResult;

public class IsvExceptionHandlerTest {

	@Test
	public void handleIsvServiceException_returnsTheApiResultInIt() throws Exception {
		IsvServiceException someException = new IsvServiceException(UNAUTHORIZED, "no no no");

		APIResult result = new IsvExceptionHandler().handleIsvServiceException(someException);

		assertThat(result).isEqualTo(unauthorized("no no no"));
	}

	private APIResult unauthorized(String message) {
		return new APIResult(UNAUTHORIZED, message);
	}
}
