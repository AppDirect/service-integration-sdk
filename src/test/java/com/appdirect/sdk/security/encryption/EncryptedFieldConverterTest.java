package com.appdirect.sdk.security.encryption;

import static com.appdirect.sdk.utils.ConstantUtils.ENCRYPTED_DATA;
import static com.appdirect.sdk.utils.ConstantUtils.TEST_ENCRYPTION_DATA;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.appdirect.sdk.security.encryption.converter.EncryptedFieldConverter;
import com.appdirect.sdk.security.encryption.service.EncryptionService;

public class EncryptedFieldConverterTest {
	@Mock
	private EncryptionService encryptionService;

	private EncryptedFieldConverter encryptedFieldConverter;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);
		encryptedFieldConverter = new EncryptedFieldConverter();
		ReflectionTestUtils.setField(encryptedFieldConverter, "encryptionService", encryptionService);
	}

	@Test
	public void testConvertToDatabaseColumn() {
		when(encryptionService.encrypt(TEST_ENCRYPTION_DATA)).thenReturn(ENCRYPTED_DATA);
		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

		encryptedFieldConverter.convertToDatabaseColumn(TEST_ENCRYPTION_DATA);

		verify(encryptionService, times(1)).encrypt(argumentCaptor.capture());
		assertThat(TEST_ENCRYPTION_DATA).isEqualTo(argumentCaptor.getValue());
	}

	@Test
	public void testConvertToEntityAttribute() {
		when(encryptionService.decrypt(ENCRYPTED_DATA)).thenReturn(TEST_ENCRYPTION_DATA);
		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

		encryptedFieldConverter.convertToEntityAttribute(ENCRYPTED_DATA);

		verify(encryptionService, times(1)).decrypt(argumentCaptor.capture());
		assertThat(ENCRYPTED_DATA).isEqualTo(argumentCaptor.getValue());
	}
}
