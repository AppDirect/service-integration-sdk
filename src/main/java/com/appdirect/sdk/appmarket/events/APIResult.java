package com.appdirect.sdk.appmarket.events;

import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class APIResult {
	private boolean success;
	private ErrorCode errorCode;
	private String message;
	private String accountIdentifier;
	private String userIdentifier;
	@JsonIgnore
	private int statusCodeReturnedToAppmarket;

	public APIResult(ErrorCode errorCode, String message) {
		this(false, message);
		this.errorCode = errorCode;
	}

	public APIResult(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public static APIResult success(String message) {
		APIResult result = new APIResult(true, message);
		result.setStatusCodeReturnedToAppmarket(200);
		return result;
	}

	public static APIResult asyncEventResult(String message) {
		APIResult result = new APIResult(true, message);
		result.setStatusCodeReturnedToAppmarket(202);
		return result;
	}

	/**
	 * Creates a failed result with the given error code and message.
	 * Note: HTTP OK (200) will be returned to the appmarket; all responses
	 * going to the appmarket need to have a 200 status, whether they are successful or not.
	 *
	 * @param errorCode the code of the error
	 * @param message   human-readable error message that explains the issue.
	 * @return the failed result object
	 */
	public static APIResult failure(ErrorCode errorCode, String message) {
		APIResult result = new APIResult(errorCode, message);
		result.setStatusCodeReturnedToAppmarket(200);
		return result;
	}
}
