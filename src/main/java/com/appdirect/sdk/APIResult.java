package com.appdirect.sdk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class APIResult {
	private boolean success;
	private boolean asynchronous = false;
	private ErrorCode errorCode;
	private String message;
	private String accountIdentifier;
	private String userIdentifier;
	private String id;

	public APIResult(ErrorCode errorCode, String message) {
		setSuccess(false);
		setErrorCode(errorCode);
		setMessage(message);
	}

	public APIResult(boolean success, String message) {
		setSuccess(success);
		setMessage(message);
	}

	public static APIResult success(String message) {
		return new APIResult(true, message);
	}

	public static APIResult failure(ErrorCode errorCode, String message) {
		return new APIResult(errorCode, message);
	}
}
