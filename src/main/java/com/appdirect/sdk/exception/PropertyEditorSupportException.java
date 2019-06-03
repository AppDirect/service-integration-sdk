package com.appdirect.sdk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PropertyEditorSupportException extends RuntimeException {
	public PropertyEditorSupportException(String message) {
		super(message);
	}

	public PropertyEditorSupportException(String template, Object... args) {
		this(String.format(template, args));
	}
}
