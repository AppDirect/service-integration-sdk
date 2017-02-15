package com.appdirect.sdk.feature;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.appdirect.sdk.feature.sample_connector.full.FullConnector;
import com.appdirect.sdk.support.FakeAppmarket;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FullConnector.class, webEnvironment = RANDOM_PORT)
public class CanValidateISVDataIntegrationTest {
	@LocalServerPort
	private int localConnectorPort;
	private FakeAppmarket fakeAppmarket;
	@Autowired
	private ObjectMapper objectMapper;
	private static final String CUSTOMER_ACCOUNT_API_END_PONT = "/api/v1/migration/validateCustomerAccount";
	private static final String SUBSCRIPTION_API_END_PONT = "/api/v1/migration/validateSubscription";

	@Before
	public void setUp() throws Exception {
		fakeAppmarket = FakeAppmarket.create(localConnectorPort + 1, "isv-key", "isv-secret").start();
	}

	@After
	public void stop() throws Exception {
		fakeAppmarket.stop();
	}

	@Test
	public void validateCustomerAccount() throws Exception {
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("tenantId", "3379191");
		dataMap.put("tenantDomain", "test.org");
		dataMap.put("externalVendorId", UUID.randomUUID().toString());
		dataMap.put("customerName", "Test Org");

		String jsonData = objectMapper.writeValueAsString(dataMap);
		HttpResponse response = fakeAppmarket.sendSignedPostRequestTo(baseConnectorUrl() + CUSTOMER_ACCOUNT_API_END_PONT, new StringEntity(jsonData, ContentType.APPLICATION_JSON));

		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);
		assertThat(EntityUtils.toString(response.getEntity())).isEqualTo("{\"success\":false,\"errorCode\":\"CONFIGURATION_ERROR\",\"message\":\"Customer account validation is not supported.\"}");
	}

	@Test
	public void validateSubscription() throws Exception {
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("subscriptionId", "76547391");
		dataMap.put("sku", "Google-Apps-For-Business");
		String jsonData = objectMapper.writeValueAsString(dataMap);
		HttpResponse response = fakeAppmarket.sendSignedPostRequestTo(baseConnectorUrl() + SUBSCRIPTION_API_END_PONT, new StringEntity(jsonData, ContentType.APPLICATION_JSON));

		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);
		Map<String, Object>result = objectMapper.readValue(response.getEntity().getContent(), HashMap.class);
		assertThat(result.size()).isEqualTo(3);
		assertThat(result.get("errorCode")).isEqualTo("CONFIGURATION_ERROR");
		assertThat(result.get("message")).isEqualTo("Subscription validation is not supported.");
		assertThat(result.get("success")).isEqualTo(false);
	}

	private String baseConnectorUrl() {
		return "http://localhost:" + localConnectorPort;
	}
}
