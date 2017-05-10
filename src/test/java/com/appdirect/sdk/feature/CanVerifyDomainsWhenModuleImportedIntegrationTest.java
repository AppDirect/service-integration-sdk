package com.appdirect.sdk.feature;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.appdirect.sdk.appmarket.domain.DnsOwnershipVerificationRecords;
import com.appdirect.sdk.appmarket.domain.MxDnsRecord;
import com.appdirect.sdk.appmarket.domain.TxtDnsRecord;
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

		TxtDnsRecord expectedTxtRecord = new TxtDnsRecord(
				"@",
				3600,
				"key1=value1");
		MxDnsRecord expectedMxRecord = new MxDnsRecord(
				"@",
				3600,
				1,
				"abc.example.com."
		);

		//When
		ResponseEntity<DnsOwnershipVerificationRecords> actualRecord = restTemplate.exchange(
				ownershipProofEndpoint(testCustomerId, testDomain),
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<DnsOwnershipVerificationRecords>() {
				}
		);

		//Then
		assertThat(actualRecord.getBody().getTxt()).containsExactly(expectedTxtRecord);
		assertThat(actualRecord.getBody().getMx()).containsExactly(expectedMxRecord);
	}

	public String ownershipProofEndpoint(String customerId, String domain) {
		return String.format(DOMAIN_OWNERSHIP_PROOF_ENDPOINT_TEMPLATE, localConnectorPort, customerId, domain);
	}
}
