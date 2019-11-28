package com.appdirect.sdk.security.encryption.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.security.encryption.service.AESEncryptionServiceImpl;
import com.appdirect.sdk.security.encryption.service.EncryptionService;

@Configuration
public class EncryptionConfiguration {
	@Bean
	public EncryptionService encryptionService() {
		return new AESEncryptionServiceImpl();
	}
}
