package com.appdirect.sdk.meteredUsage.exception;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum APIErrorCode {
	UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR.value(), "There was an unknown error during the execution of the service"),
	BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED.value(), "Unable to authenticate, invalid Oauth1 credentials"),
	BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "Invalid Request"),
	SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE.value(), "Service unavailable"),
	NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Service not found");

	private int statusCode;
	private String description;

	public static APIErrorCode getByDescriptionAndStatusCode(int statusCode) {
		return Stream.of(APIErrorCode.values()).filter(er -> er.statusCode == statusCode)
				.findFirst().orElse(UNKNOWN);
	}
}
