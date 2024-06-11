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

package com.appdirect.sdk.feature;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.appdirect.sdk.feature.sample_connector.full.FullConnector;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FullConnector.class, webEnvironment = RANDOM_PORT)
public class OrderValidationIsWiredUpIntegrationTest {
	@LocalServerPort
	private int localConnectorPort;
	private HttpClient httpClient = createHttpClient();

	@Test
	public void testValidationEdnpoint_whenPostSent_DefaultHandlerRespondsWith200() throws Exception {
		//Given
		HttpPost httpPost = new HttpPost(format("http://localhost:%d/unsecured/integration/orderValidation", localConnectorPort));
		List<NameValuePair> keys = new ArrayList<>();
		keys.add(new BasicNameValuePair("aKey", "aValue"));
		httpPost.setEntity(new UrlEncodedFormEntity(keys));

		//When
		HttpResponse response = httpClient.execute(httpPost);

		//Then
		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
	}

	private CloseableHttpClient createHttpClient() {
		return HttpClients.custom()
				.setUserAgent("Apache-HttpClient/4.3.6 (java 1.5)")
				.setDefaultHeaders(
						asList(
								new BasicHeader(HttpHeaders.ACCEPT, "application/json, application/xml"),
								new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate"),
								new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
						)
				)
				.disableRedirectHandling()
				.build();
	}
}
