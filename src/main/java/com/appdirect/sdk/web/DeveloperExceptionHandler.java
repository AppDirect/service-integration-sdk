package com.appdirect.sdk.web;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.appdirect.sdk.APIResult;
import com.appdirect.sdk.exception.DeveloperServiceException;

@Slf4j
@ControllerAdvice
public class DeveloperExceptionHandler {
	@ResponseBody
	@ResponseStatus(value= HttpStatus.OK)
	@ExceptionHandler
	public APIResult handleDeveloperServiceException(DeveloperServiceException e) {
		APIResult result = e.getResult();
		log.debug("Handling DeveloperServiceException. APIResult={}", result);
		return result;
	}
}
