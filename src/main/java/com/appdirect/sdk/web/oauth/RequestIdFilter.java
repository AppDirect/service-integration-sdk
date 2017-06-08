package com.appdirect.sdk.web.oauth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestIdFilter implements Filter {
	public static final String REQUEST_ID_HEADER = "x-request-id";
	public static final String MDC_REQUEST_ID_KEY = "requestId";

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String header = request.getHeader(REQUEST_ID_HEADER);
		MDC.put(MDC_REQUEST_ID_KEY, header);
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Do nothing
	}

	@Override
	public void destroy() {
		// Do nothing
	}
}
