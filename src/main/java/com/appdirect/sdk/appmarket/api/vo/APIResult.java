package com.appdirect.sdk.appmarket.api.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class APIResult implements Serializable {
	private static final long serialVersionUID = -5781176648994756885L;

	private boolean success;
	private boolean asynchronous = false;
	private ErrorCode errorCode;
	private String message;
	private String accountIdentifier;
	private String userIdentifier;
	private String id;

	public static APIResult success(String message) {
		return new APIResult(true, message);
	}

	public APIResult(ErrorCode errorCode, String message) {
		setSuccess(false);
		setErrorCode(errorCode);
		setMessage(message);
	}

	public APIResult(boolean success, String message) {
		setSuccess(success);
		setMessage(message);
	}
}
