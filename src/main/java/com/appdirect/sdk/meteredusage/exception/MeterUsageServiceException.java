package com.appdirect.sdk.meteredusage.exception;

import lombok.Getter;

@Getter
public final class MeterUsageServiceException extends RuntimeException {

	private int errorCode;

	public MeterUsageServiceException(int errorCode, String message){
		super(message);
		this.errorCode = errorCode;
	}
}
