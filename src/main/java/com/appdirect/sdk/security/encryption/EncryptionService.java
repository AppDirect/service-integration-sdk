package com.appdirect.sdk.security.encryption;

public interface EncryptionService {
	String encrypt(String value) throws EncryptionException;
	String decrypt(String value) throws EncryptionException;
}
