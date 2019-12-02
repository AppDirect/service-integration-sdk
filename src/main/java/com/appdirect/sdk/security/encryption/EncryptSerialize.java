package com.appdirect.sdk.security.encryption;

import java.io.IOException;

import lombok.RequiredArgsConstructor;

import com.appdirect.sdk.security.encryption.service.EncryptionService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@RequiredArgsConstructor
public class EncryptSerialize extends JsonSerializer<String> {

	private final EncryptionService encryptionService;

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(encryptionService.encrypt(value));
	}
}
