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
 * if they are using the {@link DomainDnsOwnershipVerificationConfiguration}. The implementation of this class specifies the
 * {@link TxtDnsRecord} that is returned to the monolith upon querying.
 */
public interface DomainDnsVerificationInfoHandler {
	DnsOwnershipVerificationRecords readOwnershipVerificationRecords(String customerId, String domain);
}
