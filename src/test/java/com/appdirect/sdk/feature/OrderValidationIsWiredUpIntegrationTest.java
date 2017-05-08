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
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
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
