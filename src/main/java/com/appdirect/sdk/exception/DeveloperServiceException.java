package com.appdirect.sdk.exception;

import lombok.Getter;

import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.appmarket.events.ErrorCode;

public class DeveloperServiceException extends RuntimeException {
	private static final long serialVersionUID = 6079855456255852065L;

	@Getter
	private final APIResult result;

	public DeveloperServiceException(String message) {
		super(message);
		this.result = new APIResult(false, message);
	}

	public DeveloperServiceException(ErrorCode errorCode, String message) {
		super(message);
		this.result = new APIResult(errorCode, message);
	}
}
