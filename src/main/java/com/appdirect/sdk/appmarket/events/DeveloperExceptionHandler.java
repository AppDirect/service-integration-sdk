package com.appdirect.sdk.appmarket.events;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appdirect.sdk.exception.DeveloperServiceException;

@Slf4j
@ControllerAdvice
public class DeveloperExceptionHandler {
	@ResponseBody
	@ExceptionHandler
	public APIResult handleDeveloperServiceException(DeveloperServiceException e, HttpServletResponse response) {
		APIResult result = e.getResult();
		response.setStatus(result.getStatusCodeReturnedToAppmarket());
		log.debug("Handling DeveloperServiceException. APIResult={}", result);
		return result;
	}
}
