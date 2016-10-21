package com.appdirect.sdk.isv.api.model.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class APIResult implements Serializable {
	private static final long serialVersionUID = -5781176648994756885L;

	private boolean success;
	private boolean asynchronous = false;
	private ErrorCode errorCode;
	private String message;
	private String accountIdentifier;
	private String userIdentifier;
	private String id;

	public APIResult(ErrorCode errorCode, String message) {
		super();
		setSuccess(false);
		setErrorCode(errorCode);
		setMessage(message);
	}

	public APIResult(boolean success, String message) {
		super();
		setSuccess(success);
		setMessage(message);
	}
}
