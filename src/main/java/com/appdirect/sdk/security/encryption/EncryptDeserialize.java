package com.appdirect.sdk.security.encryption;


import com.appdirect.sdk.security.encryption.configuration.EncryptionConfiguration;
import com.appdirect.sdk.security.encryption.service.EncryptionService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

@AllArgsConstructor
public class EncryptDeserialize extends JsonDeserializer<String> {


	private final EncryptionService encryptionService;

	@Override
	public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		return encryptionService.decrypt(jsonParser.getValueAsString());
	}

	public EncryptDeserialize(){
		ApplicationContext context = new AnnotationConfigApplicationContext(EncryptionConfiguration.class);
		encryptionService = context.getBean(EncryptionService.class);
	}
}
