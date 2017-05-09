package com.appdirect.sdk.feature;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.appdirect.sdk.appmarket.domain.TXTDnsRecord;
import com.appdirect.sdk.appmarket.domain.TxtRecordItem;
import com.appdirect.sdk.feature.sample_connector.full.FullConnector;
import com.appdirect.sdk.web.oauth.OAuthSignedClientHttpRequestFactory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FullConnector.class, webEnvironment = RANDOM_PORT)
public class CanVerifyDomainsWhenModuleImportedIntegrationTest {
	@LocalServerPort
	private int localConnectorPort;
	@Autowired
	private ApplicationContext applicationContext;

	private static String testOauthKey;
	private static String testOauthSecret;
	private static RestTemplate restTemplate;

	static {
		testOauthKey = "isv-key";
		testOauthSecret = "isv-secret";
		restTemplate = new RestTemplate(new OAuthSignedClientHttpRequestFactory(testOauthKey, testOauthSecret));
	}

	private static final String DOMAIN_OWNERSHIP_PROOF_ENDPOINT_TEMPLATE =
			"http://localhost:%d/api/v1/integration/customers/%s/domains/%s/ownershipProofRecord";

	@Test
	public void testValidateOwnership_whenCalled_txtRecordIsReturned() throws Exception {
		//Given
		String testCustomerId = "testCustomerId";
		String testDomain = "example.com";

		List<TxtRecordItem> expectedEntries = Arrays.asList(
				new TxtRecordItem("customerIdentifier", testCustomerId),
				new TxtRecordItem("domain", testDomain)
		);

		TXTDnsRecord expectedRecord = new TXTDnsRecord(
				"@",
				3600,
				"TXT",
				new HashSet<>(expectedEntries));

		//When
		TXTDnsRecord actualRecord = restTemplate.getForObject(
				ownershipProofEndpoint(testCustomerId, testDomain),
				TXTDnsRecord.class
		);

		//Then
		assertThat(actualRecord).isEqualTo(expectedRecord);
	}

	public String ownershipProofEndpoint(String customerId, String domain) {
		return String.format(DOMAIN_OWNERSHIP_PROOF_ENDPOINT_TEMPLATE, localConnectorPort, customerId, domain);
	}
}
