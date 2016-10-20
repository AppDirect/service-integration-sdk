package com.appdirect.isv.service.consumer;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.appdirect.isv.api.model.vo.EventInfo;
import com.appdirect.isv.service.exception.IsvEventConsumerExceptionHandler;
import com.appdirect.isv.service.oauth.TwoLeggedOAuthClientHttpRequestFactory;

@Slf4j
@Service
public class IsvEventConsumerImpl implements IsvEventConsumer {
	@Autowired
	private IsvEventConsumerExceptionHandler errorHandler;

	private RestOperations restOperations(String key, String secret) {
		RestTemplate restTemplate = new RestTemplate(new TwoLeggedOAuthClientHttpRequestFactory(key, secret));
		restTemplate.setErrorHandler(errorHandler);
		return restTemplate;
	}

	@Override
	public EventInfo consumeToken(String url, String key, String secret) {
		log.debug("Consuming event from = {}", url);
		return restOperations(key, secret).getForObject(url, EventInfo.class);
	}
}
