package com.appdirect.sdk.exception;

import lombok.Getter;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.ErrorCode;

@Getter
public class DeveloperServiceException extends RuntimeException {
	private static final long serialVersionUID = 6079855456255852065L;

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
