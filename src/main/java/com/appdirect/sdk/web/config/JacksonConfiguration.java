package com.appdirect.sdk.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
@ConditionalOnClass(ObjectMapper.class)
public class JacksonConfiguration { // NOSONAR: squid:S1118 - "Add a private constructor to hide the implicit public one" - not an utility class, no one will derive from it.
	@Bean
	public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
		return builder
				.createXmlMapper(false)
				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.build();
	}
}
