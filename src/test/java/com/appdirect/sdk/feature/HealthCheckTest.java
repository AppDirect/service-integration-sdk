package com.appdirect.sdk.feature;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.appdirect.sdk.feature.sample_connector.full.FullConnector;
import com.appdirect.sdk.support.HttpClientHelper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FullConnector.class, webEnvironment = RANDOM_PORT)
public class HealthCheckTest {
	@LocalServerPort
	private int localConnectorPort;
	private HttpClient httpClient = HttpClients.createMinimal();

	@Test
	public void healthEndpointReturnsSuccess() throws Exception {
		//Given
		HttpGet httpGet = HttpClientHelper.get(
			format("http://localhost:%d/health", localConnectorPort)
		);

		//When
		HttpResponse response = httpClient.execute(httpGet);

		//Then
		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
	}
}
