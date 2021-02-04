package com.appdirect.sdk.web.oauth;

import javax.servlet.Filter;

public interface OAuth2AuthorizationService {
    Filter getOAuth2Filter();
}
