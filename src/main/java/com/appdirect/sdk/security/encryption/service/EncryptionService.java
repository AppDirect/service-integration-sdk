package com.appdirect.sdk.security.encryption.service;

import com.appdirect.sdk.security.encryption.EncryptionException;

public interface EncryptionService {
	String encrypt(String value) throws EncryptionException;
	String decrypt(String value) throws EncryptionException;
}
