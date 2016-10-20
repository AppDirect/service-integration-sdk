package com.appdirect.isv.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.appdirect.isv.api.model.vo.APIResult;
import com.appdirect.isv.exception.IsvServiceException;

@Slf4j
@ControllerAdvice
public class IsvExceptionHandler {
	@ResponseBody
	@ResponseStatus(value= HttpStatus.OK)
	@ExceptionHandler(IsvServiceException.class)
	public APIResult handleIsvServiceException(IsvServiceException e) {
		APIResult result = e.getResult();
		log.debug("Handling IsvServiceException. APIResult = {}", result);
		return result;
	}
}
