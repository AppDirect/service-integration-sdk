package com.appdirect.sdk.notification;

public class SendNotificationFailedException extends Exception {
	private static final long serialVersionUID = -3487516993124221348L;

	SendNotificationFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
