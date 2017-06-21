package com.appdirect.sdk.exception;

import lombok.Getter;


import org.springframework.http.HttpStatus;

import com.appdirect.sdk.appmarket.usersync.UserSyncApiResult;

public class UserSyncException extends RuntimeException {
	private static final long serialVersionUID = 8765855456255852065L;

	@Getter
	private UserSyncApiResult result;

	public UserSyncException(HttpStatus status, String errorCode, String message) {
		super(message);
		this.result = new UserSyncApiResult(status, errorCode, message);
	}

	public UserSyncException(String message, UserSyncApiResult userSyncApiResult) {
		super(message);
		this.result = userSyncApiResult;
	}
}
