package com.appdirect.sdk.security.encryption;

import java.io.IOException;

import lombok.RequiredArgsConstructor;

import com.appdirect.sdk.security.encryption.service.EncryptionService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

@RequiredArgsConstructor
public class EncryptDeserialize extends JsonDeserializer<String> {

	private final EncryptionService encryptionService;

	@Override
	public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		return encryptionService.decrypt(jsonParser.getValueAsString());
	}
}
