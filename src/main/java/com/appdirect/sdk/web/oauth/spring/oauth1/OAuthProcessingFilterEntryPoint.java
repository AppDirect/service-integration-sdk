package com.appdirect.sdk.web.oauth.spring.oauth1;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth.common.signature.UnsupportedSignatureMethodException;
import org.springframework.security.oauth.provider.InvalidOAuthParametersException;
import org.springframework.security.web.AuthenticationEntryPoint;

/** @deprecated */
@Deprecated
public class OAuthProcessingFilterEntryPoint implements AuthenticationEntryPoint {
    private String realmName;

    public OAuthProcessingFilterEntryPoint() {
    }

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof InvalidOAuthParametersException) {
            response.sendError(400, authException.getMessage());
        } else if (authException.getCause() instanceof UnsupportedSignatureMethodException) {
            response.sendError(400, authException.getMessage());
        } else {
            StringBuilder headerValue = new StringBuilder("OAuth");
            if (this.realmName != null) {
                headerValue.append(" realm=\"").append(this.realmName).append('"');
            }

            response.addHeader("WWW-Authenticate", headerValue.toString());
            response.sendError(401, authException.getMessage());
        }

    }

    public String getRealmName() {
        return this.realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }
}
