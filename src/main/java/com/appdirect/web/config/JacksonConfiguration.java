package com.appdirect.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
@ConditionalOnClass(ObjectMapper.class)
public class JacksonConfiguration {

    @Configuration
    @ConditionalOnClass({ObjectMapper.class, Jackson2ObjectMapperBuilder.class})
    static class JacksonObjectMapperConfiguration {

        @Bean
        public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
            return builder
                .createXmlMapper(false)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        }
    }
}
