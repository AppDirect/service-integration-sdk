package com.appdirect.sdk.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class DomainServiceException extends RuntimeException {
	private static final long serialVersionUID = -2386693976020881018L;

	private final String message;
	private final List<String> params = new ArrayList<>();

	public DomainServiceException(String message) {
		super(message);
		this.message = message;
	}

	public DomainServiceException(String message, List<String> params) {
		super(message);
		this.message = message;
		this.params.addAll(params);
	}
}
