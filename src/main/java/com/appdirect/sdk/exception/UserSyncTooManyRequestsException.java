package com.appdirect.sdk.exception;

/**
 * Exception thrown when the Marketplace cannot process requests
 */
public class UserSyncTooManyRequestsException extends RuntimeException {

	public UserSyncTooManyRequestsException(String message) {
		super(message);
	}
}
