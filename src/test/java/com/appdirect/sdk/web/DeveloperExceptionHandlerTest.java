package com.appdirect.sdk.web;

import static com.appdirect.sdk.appmarket.events.ErrorCode.UNAUTHORIZED;
import static org.assertj.core.api.Assertions.assertThat;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.appmarket.events.DeveloperExceptionHandler;
import com.appdirect.sdk.exception.DeveloperServiceException;

public class DeveloperExceptionHandlerTest {

	@Test
	public void handleIsvServiceException_returnsTheApiResultInIt_setsTheStatusTo200() throws Exception {
		HttpServletResponse response = new MockHttpServletResponse();
		DeveloperServiceException someException = new DeveloperServiceException(UNAUTHORIZED, "no no no");

		APIResult result = new DeveloperExceptionHandler().handleDeveloperServiceException(someException, response);

		assertThat(response.getStatus()).isEqualTo(200);
		assertThat(result).hasFieldOrPropertyWithValue("errorCode", UNAUTHORIZED)
				.hasFieldOrPropertyWithValue("message", "no no no");
	}
}
