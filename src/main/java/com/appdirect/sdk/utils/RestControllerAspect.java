package com.appdirect.sdk.utils;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;

@Aspect
public class RestControllerAspect {

	public static final String REQUEST_ID_HEADER = "x-request-id";
	public static final String MDC_REQUEST_ID_KEY = "requestId";

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void beanAnnotatedWithRestController () {}

	@Pointcut("execution(public * *(..))")
	public void publicMethod() {}

	@Pointcut("publicMethod() && beanAnnotatedWithRestController()")
	public void publicMethodInsideAClassMarkedWithAtMonitor() {}

	@Pointcut("args(request, ..)")
	public void withHttpServletRequestArgument(HttpServletRequest request) {}

	@Before(value = "publicMethodInsideAClassMarkedWithAtMonitor() && withHttpServletRequestArgument(request)")
	public void storeRequestIdBefore (HttpServletRequest request) {
		String header = request.getHeader(REQUEST_ID_HEADER);
		MDC.put(MDC_REQUEST_ID_KEY, header);
	}
}
