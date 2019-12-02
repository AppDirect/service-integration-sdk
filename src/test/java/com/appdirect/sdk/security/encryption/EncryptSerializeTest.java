package com.appdirect.sdk.security.encryption;

import static com.appdirect.sdk.utils.ConstantUtils.ENCRYPTED_DATA;
import static com.appdirect.sdk.utils.ConstantUtils.TEST_ENCRYPTION_DATA;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.appdirect.sdk.security.encryption.service.EncryptionService;
import com.fasterxml.jackson.core.JsonGenerator;

public class EncryptSerializeTest {
	@Mock
	private EncryptionService mockEncryptionService;
	@Mock
	private JsonGenerator jsonGenerator;
	private EncryptSerialize encryptSerialize;

	@BeforeMethod
	public void setUp() {
		initMocks(this);
		encryptSerialize = new EncryptSerialize(mockEncryptionService);
	}

	@Test
	public void testSerialize_success() throws IOException {
		when(mockEncryptionService.encrypt(TEST_ENCRYPTION_DATA)).thenReturn(ENCRYPTED_DATA);
		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
		
		encryptSerialize.serialize(TEST_ENCRYPTION_DATA, jsonGenerator, null);

		verify(mockEncryptionService, times(1)).encrypt(argumentCaptor.capture());
		verify(jsonGenerator, times(1)).writeString(ENCRYPTED_DATA);
		assertThat(TEST_ENCRYPTION_DATA).isEqualTo(argumentCaptor.getValue());
	}
}
