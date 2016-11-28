package com.appdirect.sdk.web.exception;

import static com.appdirect.sdk.appmarket.events.ErrorCode.NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import com.appdirect.sdk.exception.DeveloperServiceException;

public class AppmarketEventConsumerExceptionHandlerTest {

	private AppmarketEventConsumerExceptionHandler handler;

	@Before
	public void setup() throws Exception {
		handler = new AppmarketEventConsumerExceptionHandler();
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
	public void notFoundResponse_throwsNotFoundException() throws Exception {
		assertThatThrownBy(() -> handler.handleError(notFoundResponse()))
				.isInstanceOf(DeveloperServiceException.class)
				.hasMessage("Failed to fetch event: Not Found")
				.hasFieldOrPropertyWithValue("result.errorCode", NOT_FOUND);
	}

	@Test
	public void noContentResponse_throwsGenericException() throws Exception {
		assertThatThrownBy(() -> handler.handleError(noContentResponse()))
				.isInstanceOf(DeveloperServiceException.class)
				.hasMessage("Failed to fetch event: No Content")
				.hasFieldOrPropertyWithValue("result.errorCode", null);
	}

	private ClientHttpResponse notFoundResponse() throws IOException {
		return aResponse(HttpStatus.NOT_FOUND);
	}

	private ClientHttpResponse noContentResponse() throws IOException {
		return aResponse(HttpStatus.NO_CONTENT);
	}

	private ClientHttpResponse okResponse() throws IOException {
		return aResponse(HttpStatus.OK);
	}

	private ClientHttpResponse aResponse(HttpStatus status) throws IOException {
		ClientHttpResponse response = mock(ClientHttpResponse.class);
		when(response.getStatusCode()).thenReturn(status);
		when(response.getStatusText()).thenReturn(status.getReasonPhrase());
		return response;
	}
}
