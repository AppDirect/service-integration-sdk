package com.appdirect.sdk.web.exception;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.CharEncoding;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.appdirect.sdk.appmarket.usersync.UserSyncApiResult;
import com.appdirect.sdk.exception.UserSyncException;
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
		try {
			UserSyncApiResult userSyncResultWS = createUserSyncApiResult(clientHttpResponse);
			throw new UserSyncException(userSyncResultWS.getMessage(), userSyncResultWS);
		} catch (JsonProcessingException e) {
			String formatString = String.format("Wrong format of the error returned in UserSync Response statusCode=%s statusText=%s",	clientHttpResponse.getStatusCode(),	clientHttpResponse.getStatusText());
			log.error(formatString, e);
			throw new UserSyncException(formatString, createEmptyApiResult(clientHttpResponse));
		} catch (IOException e) {
			String errorString = String.format("There was an IO error processing error statusCode=%s statusText=%s",	clientHttpResponse.getStatusCode(),	clientHttpResponse.getStatusText());
			log.error(errorString, e);
			throw new UserSyncException(errorString, createEmptyApiResult(clientHttpResponse));
		}
	}

	private UserSyncApiResult createUserSyncApiResult(ClientHttpResponse clientHttpResponse) throws IOException {
		if (clientHttpResponse.getBody() == null) {
			return createEmptyApiResult(clientHttpResponse);
		}
		String error = IOUtils.toString(clientHttpResponse.getBody(), CharEncoding.UTF_8);
		UserSyncApiResult userSyncApiResult = objectMapper.readValue(error, UserSyncApiResult.class);
		userSyncApiResult.setStatus(clientHttpResponse.getStatusCode());
		return userSyncApiResult;
	}

	private UserSyncApiResult createEmptyApiResult(ClientHttpResponse clientHttpResponse) throws IOException {
		return new UserSyncApiResult(clientHttpResponse.getStatusCode(), "", "");
	}
}
