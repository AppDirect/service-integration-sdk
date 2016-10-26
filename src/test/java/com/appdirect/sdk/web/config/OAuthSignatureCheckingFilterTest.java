package com.appdirect.sdk.web.config;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.oauth.common.OAuthException;
import org.springframework.security.oauth.common.signature.OAuthSignatureMethod;
import org.springframework.security.oauth.common.signature.OAuthSignatureMethodFactory;
import org.springframework.security.oauth.common.signature.UnsupportedSignatureMethodException;
import org.springframework.security.oauth.provider.ConsumerAuthentication;
import org.springframework.security.oauth.provider.ConsumerCredentials;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.token.OAuthProviderToken;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenImpl;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;

import com.appdirect.sdk.web.oauth.ConnectorConsumerDetails;

public class OAuthSignatureCheckingFilterTest {

	private OAuthSignatureCheckingFilter filter;
	private OAuthProviderTokenServices tokenServices;
	private OAuthSignatureMethodFactory methodFactory;

	@Before
	public void setup() throws Exception {
		tokenServices = mock(OAuthProviderTokenServices.class);
		methodFactory = mock(OAuthSignatureMethodFactory.class);

		filter = new OAuthSignatureCheckingFilter();
		filter.setTokenServices(tokenServices);
		filter.setSignatureMethodFactory(methodFactory);
	}

	@Test
	public void validateSignature_wrapsUnsupportedSignatureExceptions() throws Exception {
		when(methodFactory.getSignatureMethod(eq("IMPOSSIBLE METHOD"), any(), anyString())).thenThrow(new UnsupportedSignatureMethodException("bad method"));

		ConsumerDetails details = details("some-key", "some-secret");
		ConsumerCredentials credentials = impossibleMethodCredentials();

		assertThatThrownBy(() -> filter.validateSignature(new ConsumerAuthentication(details, credentials)))
				.isInstanceOf(OAuthException.class)
				.hasMessage("bad method")
				.hasCauseExactlyInstanceOf(UnsupportedSignatureMethodException.class);
	}

	@Test
	public void validateSignature_verifiesSignatureWithMethod() throws Exception {
		OAuthSignatureMethod theMethod = givenFactoryReturnsSha1Method();

		ConsumerDetails details = details("a-key", "the-secret");
		ConsumerCredentials credentials = sha1Credentials("actual-signature", "base-string");

		filter.validateSignature(new ConsumerAuthentication(details, credentials));

		verify(theMethod).verify("base-string", "actual-signature");
	}

	@Test
	public void validateSignature_usesTokenWhenUsedInCredentials() throws Exception {
		OAuthProviderToken theToken = someToken("the-secret-of-the-token");
		when(tokenServices.getToken("token-of-the-credentials")).thenReturn(theToken);

		OAuthSignatureMethod theMethod = givenFactoryReturnsSha1Method();
		when(methodFactory.getSignatureMethod(eq("SHA1"), any(), eq("the-secret-of-the-token"))).thenReturn(theMethod);

		ConsumerDetails details = details("a-key", "the-secret-of-the-consumer");
		ConsumerCredentials credentialsWithToken = sha1Credentials("actual-signature", "base-string", "token-of-the-credentials");

		filter.validateSignature(new ConsumerAuthentication(details, credentialsWithToken));

		verify(theMethod).verify("base-string", "actual-signature");
	}

	@Test
	public void validateSignature_usesHttpsWhenPropertyIsTrue() throws Exception {
		OAuthSignatureMethod theMethod = givenFactoryReturnsSha1Method();

		ConsumerDetails details = details("a-key", "some-secret");
		ConsumerCredentials credentials = sha1Credentials("actual-signature", "http://should-be-https.com");

		enableHttpReplacementIn(filter);
		filter.validateSignature(new ConsumerAuthentication(details, credentials));

		verify(theMethod).verify(eq("https://should-be-https.com"), anyString());
	}

	private void enableHttpReplacementIn(OAuthSignatureCheckingFilter filter) throws IllegalAccessException, NoSuchFieldException {
		Field field = OAuthSignatureCheckingFilter.class.getDeclaredField("signatureValidationUseHttps");
		field.setAccessible(true);
		field.set(filter, true);
	}

	private ConsumerCredentials sha1Credentials(String signature, String signatureBaseString) {
		return sha1Credentials(signature, signatureBaseString, null);
	}

	private ConsumerCredentials sha1Credentials(String signature, String signatureBaseString, String token) {
		return new ConsumerCredentials("some-key", signature, "SHA1", signatureBaseString, token);
	}

	private ConsumerCredentials impossibleMethodCredentials() {
		return new ConsumerCredentials("some-key", "some-signature", "IMPOSSIBLE METHOD", "some-base", null);
	}

	private ConnectorConsumerDetails details(String key, String secret) {
		return new ConnectorConsumerDetails(key, secret);
	}

	private OAuthProviderToken someToken(String secret) {
		OAuthProviderTokenImpl token = new OAuthProviderTokenImpl();
		token.setSecret(secret);
		return token;
	}

	private OAuthSignatureMethod givenFactoryReturnsSha1Method() {
		OAuthSignatureMethod method = mock(OAuthSignatureMethod.class);
		when(methodFactory.getSignatureMethod(eq("SHA1"), any(), isNull(String.class))).thenReturn(method);
		return method;
	}
}
