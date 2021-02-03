/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appdirect.sdk.web.oauth;

import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import com.appdirect.sdk.appmarket.OAuth2CredentialsSupplier;

class OAuth2ClientDetailsServiceImpl implements OAuth2ClientDetailsService {
    private final OAuth2CredentialsSupplier oAuth2CredentialsSupplier;

    OAuth2ClientDetailsServiceImpl(OAuth2CredentialsSupplier oAuth2CredentialsSupplier) {
        this.oAuth2CredentialsSupplier = oAuth2CredentialsSupplier;
    }

    @Override
    public OAuth2ProtectedResourceDetails getOAuth2ProtectedResourceDetails(String clientId) {
        return oAuth2CredentialsSupplier.getOAuth2ResourceDetails(clientId);
    }
}
