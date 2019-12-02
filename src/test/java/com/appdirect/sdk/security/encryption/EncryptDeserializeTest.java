package com.appdirect.sdk.security.encryption;

import static com.appdirect.sdk.utils.ConstantUtils.ENCRYPTED_DATA;
import static com.appdirect.sdk.utils.ConstantUtils.TEST_ENCRYPTION_DATA;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.appdirect.sdk.security.encryption.service.EncryptionService;
import com.fasterxml.jackson.core.JsonParser;

public class EncryptDeserializeTest {
	@Mock
	private EncryptionService mockEncryptionService;
	@Mock
	private JsonParser jsonParser;
	private EncryptDeserialize encryptDeserialize;

	@BeforeMethod
	public void setUp() {
		initMocks(this);
		encryptDeserialize = new EncryptDeserialize(mockEncryptionService);
	}

	@Test
	public void testSerialize_success() throws IOException {
		when(jsonParser.getValueAsString()).thenReturn(ENCRYPTED_DATA);
		when(mockEncryptionService.decrypt(ENCRYPTED_DATA)).thenReturn(TEST_ENCRYPTION_DATA);

		String result = encryptDeserialize.deserialize(jsonParser, null);

		verify(jsonParser, times(1)).getValueAsString();
		verify(mockEncryptionService, times(1)).decrypt(anyString());
		assertThat(result).isEqualTo(TEST_ENCRYPTION_DATA);
	}
}
