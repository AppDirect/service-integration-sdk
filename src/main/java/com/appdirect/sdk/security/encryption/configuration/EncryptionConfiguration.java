package com.appdirect.sdk.security.encryption.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.security.encryption.EncryptDeserialize;
import com.appdirect.sdk.security.encryption.EncryptSerialize;
import com.appdirect.sdk.security.encryption.service.EncryptionService;

@Configuration
public class EncryptionConfiguration {
	@Autowired
	private EncryptionService encryptionService;

	@Bean
	public EncryptDeserialize encryptDeserialize() {
		return new EncryptDeserialize(encryptionService);
	}
	
	@Bean
	public EncryptSerialize encryptSerialize() {
		return new EncryptSerialize(encryptionService);
	}
}
