package com.appdirect.sdk.security.encryption;

import java.io.IOException;

import lombok.AllArgsConstructor;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@AllArgsConstructor
public class EncryptSerialize extends JsonSerializer<String> {

	private EncryptionService encryptionService;

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		gen.writeString(encryptionService.encrypt(value));
	}
}
