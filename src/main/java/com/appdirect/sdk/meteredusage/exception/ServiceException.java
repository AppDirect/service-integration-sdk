package com.appdirect.sdk.meteredusage.exception;

import lombok.Getter;

@Getter
public final class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -7023031586871286653L;

	private int errorCode;

	public ServiceException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}
