package com.appdirect.sdk.exception;

import lombok.Getter;
import lombok.ToString;

import com.appdirect.sdk.marketplace.api.vo.APIResult;
import com.appdirect.sdk.marketplace.api.vo.ErrorCode;

@Getter
@ToString
public class IsvServiceException extends RuntimeException {
	private static final long serialVersionUID = 6079855456255852065L;

	private final APIResult result;

	public IsvServiceException(String message) {
		super(message);
		this.result = new APIResult(false, message);
	}

	public IsvServiceException(ErrorCode errorCode, String message) {
		super(message);
		this.result = new APIResult(errorCode, message);
	}
}
