package com.appdirect.sdk.meteredusage.exception;

import lombok.Getter;

import com.appdirect.sdk.appmarket.events.ErrorCode;

@Getter
public final class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -7023031586871286653L;

	private ErrorCode errorCode;

	public ServiceException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}
