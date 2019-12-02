package com.appdirect.sdk.security.encryption;

import static com.appdirect.sdk.utils.ConstantUtils.ENCRYPTED_DATA;
import static com.appdirect.sdk.utils.ConstantUtils.TEST_ENCRYPTION_DATA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.HashSet;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.appdirect.sdk.security.encryption.service.AESEncryptionServiceImpl;

public class AESEncryptionServiceImplTest {

	@InjectMocks
	@Spy
	private AESEncryptionServiceImpl aesEncryptionServiceImpl;

	@BeforeMethod
	public void setUp() {
		initMocks(this);
		ReflectionTestUtils.setField(aesEncryptionServiceImpl, "key", "defaultEncrypKey");
		ReflectionTestUtils.setField(aesEncryptionServiceImpl, "initVector", "RandomInitVector");
	}

	@Test
	public void testEncrypt() {
		String result = aesEncryptionServiceImpl.encrypt(TEST_ENCRYPTION_DATA);

		assertThat(result).isEqualTo(ENCRYPTED_DATA);
	}

	@Test
	public void testEncrypt_throwException() {
		doThrow(new RuntimeException()).when(aesEncryptionServiceImpl).getKey();
		
		assertThatThrownBy(() -> aesEncryptionServiceImpl.encrypt(TEST_ENCRYPTION_DATA))
				.isInstanceOf(EncryptionException.class);
	}

	@Test
	public void testDecrypt() {
		String result = aesEncryptionServiceImpl.decrypt(ENCRYPTED_DATA);

		assertThat(result).isEqualTo(TEST_ENCRYPTION_DATA);
	}

	@Test
	public void testDecrypt_throwException() {
		doThrow(new RuntimeException()).when(aesEncryptionServiceImpl).getKey();
		
		assertThatThrownBy(() -> aesEncryptionServiceImpl.decrypt(ENCRYPTED_DATA))
				.isInstanceOf(EncryptionException.class);
	}

	@Test
	public void testGetKey_smallerSize() {
		String key = "123456";
		String expected = "0000000000123456";
		ReflectionTestUtils.setField(aesEncryptionServiceImpl, "key", key);

		String result = aesEncryptionServiceImpl.getKey();

		assertThat(result).isEqualTo(expected);
	}

	@Test
	public void testGetKey_acceptedSize() {
		String key = "1234567890123456";
		ReflectionTestUtils.setField(aesEncryptionServiceImpl, "key", key);

		String result = aesEncryptionServiceImpl.getKey();

		assertThat(result).isEqualTo(key);
	}

	@Test
	public void testGetKey_biggerSize() {
		String key = "123456789012345678";
		String expected = "1234567890123456";
		ReflectionTestUtils.setField(aesEncryptionServiceImpl, "key", key);

		String result = aesEncryptionServiceImpl.getKey();

		assertThat(result).isEqualTo(expected);
	}

	@Test
	public void testGetKey_noAcceptedSize() {
		String key = "1234567890123456";
		String expected = "1234567890123456";
		ReflectionTestUtils.setField(aesEncryptionServiceImpl, "key", key);

		Set<Integer> acceptedSize = (Set<Integer>) ReflectionTestUtils.getField(aesEncryptionServiceImpl, "acceptedKeyLengths");
		ReflectionTestUtils.setField(aesEncryptionServiceImpl, "acceptedKeyLengths", new HashSet<>());
		String result = aesEncryptionServiceImpl.getKey();
		ReflectionTestUtils.setField(aesEncryptionServiceImpl, "acceptedKeyLengths", acceptedSize);

		assertThat(result).isEqualTo(expected);
	}
}
