package com.appdirect.sdk.appmarket.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The payload that is sent back to the AppMarket in response to an event
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class APIResult {
	private boolean success;
	private ErrorCode errorCode;
	private String message;
	private String accountIdentifier;
	@JsonIgnore
	private int statusCodeReturnedToAppmarket;

	public APIResult(ErrorCode errorCode, String message) {
		this(false, message);
		setErrorCode(errorCode);
	}

	public APIResult(boolean success, String message) {
		setSuccess(success);
		setMessage(message);
	}

	public static APIResult success(String message) {
		APIResult result = new APIResult(true, message);
		result.setStatusCodeReturnedToAppmarket(200);
		return result;
	}

	public static APIResult async(String message) {
		APIResult result = new APIResult(true, message);
		result.setStatusCodeReturnedToAppmarket(202);
		return result;
	}

	public static APIResult failure(ErrorCode errorCode, String message) {
		return new APIResult(errorCode, message);
	}

}
