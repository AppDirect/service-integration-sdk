package com.appdirect.sdk.web.config;

import static com.appdirect.sdk.utils.StringUtils.isEmpty;

import javax.servlet.Filter;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth.common.OAuthException;
import org.springframework.security.oauth.common.signature.OAuthSignatureMethod;
import org.springframework.security.oauth.common.signature.SignatureSecret;
import org.springframework.security.oauth.common.signature.UnsupportedSignatureMethodException;
import org.springframework.security.oauth.provider.ConsumerAuthentication;
import org.springframework.security.oauth.provider.ConsumerCredentials;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.token.OAuthProviderToken;

@Slf4j
@NoArgsConstructor
public class OAuthSignatureCheckingFilter extends ProtectedResourceProcessingFilter implements Filter {
	@Value("${signature.validation.use.https:false}")
	private Boolean signatureValidationUseHttps;

	@Override
	protected void validateSignature(ConsumerAuthentication authentication) throws AuthenticationException {
		OAuthSignatureMethod method = getSignatureMethod(authentication.getConsumerDetails(), authentication.getConsumerCredentials());
		String signatureBaseString = getSignatureBaseString(authentication);
		String signature = authentication.getConsumerCredentials().getSignature();

		method.verify(signatureBaseString, signature);
	}

	private String getSignatureBaseString(ConsumerAuthentication authentication) {
		String signatureBaseString = authentication.getConsumerCredentials().getSignatureBaseString();
		if (Boolean.TRUE.equals(signatureValidationUseHttps)) { // Validate signature against an https since load balancers can remove https
			signatureBaseString = signatureBaseString.replaceFirst("https?", "https");
		}
		return signatureBaseString;
	}

	private OAuthSignatureMethod getSignatureMethod(ConsumerDetails details, ConsumerCredentials credentials) {
		SignatureSecret secret = details.getSignatureSecret();
		OAuthProviderToken authToken = getToken(credentials);

		String signatureMethod = credentials.getSignatureMethod();
		try {
			return this.getSignatureMethodFactory().getSignatureMethod(signatureMethod, secret, authToken != null ? authToken.getSecret() : null);
		} catch (UnsupportedSignatureMethodException e) {
			throw new OAuthException(e.getMessage(), e);
		}
	}

	private OAuthProviderToken getToken(ConsumerCredentials credentials) {
		String token = credentials.getToken();
		OAuthProviderToken authToken = null;
		if (!isEmpty(token)) {
			authToken = this.getTokenServices().getToken(token);
		}
		return authToken;
	}
}
