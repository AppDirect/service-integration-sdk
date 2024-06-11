package com.appdirect.sdk.web.oauth;

import jakarta.servlet.Filter;

public interface OAuth2AuthorizationService {
    Filter getOAuth2Filter();
}
