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

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth.common.signature.SharedConsumerSecret;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.common.signature.SignatureSecret;
import org.springframework.security.oauth.provider.ExtraTrustConsumerDetails;

class ConnectorConsumerDetails implements ExtraTrustConsumerDetails {
    private static final long serialVersionUID = -6907656091309880557L;

    private final String key;
    private final SharedConsumerSecret secret;

    ConnectorConsumerDetails(String key, String secret) {
        this.key = key;
        this.secret = new SharedConsumerSecretImpl(secret);
    }

    @Override
    public String getConsumerKey() {
        return key;
    }

    @Override
    public String getConsumerName() {
        return "";
    }

    @Override
    public SignatureSecret getSignatureSecret() {
        return secret;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isRequiredToObtainAuthenticatedToken() {
        return false;
    }
}
