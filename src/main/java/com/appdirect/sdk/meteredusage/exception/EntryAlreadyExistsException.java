package com.appdirect.sdk.meteredusage.exception;

import lombok.Getter;

@Getter
public final class EntryAlreadyExistsException extends RuntimeException {

	private int errorCode;

	public EntryAlreadyExistsException(int errorCode, String message){
		super(message);
		this.errorCode = errorCode;
	}
}
