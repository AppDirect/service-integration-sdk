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

package com.appdirect.sdk.appmarket.domain;

/**
 * SDK clients must implement this interface in their {@link org.springframework.context.ApplicationContext}
 * if they are using the {@link com.appdirect.sdk.appmarket.domain.DomainDnsOwnershipVerificationConfiguration}.
 * The implementation is responsible to remove the domain from the account.
 */
@FunctionalInterface
public interface DomainRemovalHandler {
	void removeDomain(String customerId, String domain);
}
