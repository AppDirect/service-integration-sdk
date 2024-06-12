package com.appdirect.sdk.web.oauth.spring.oauth1;

import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;

/** @deprecated */
@Deprecated
public interface OAuthProviderSupport {
    Map<String, String> parseParameters(HttpServletRequest var1);

    String getSignatureBaseString(HttpServletRequest var1);
}
