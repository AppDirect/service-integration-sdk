package com.appdirect.sdk.appmarket.domain;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DomainDnsVerificationInfoControllerTest {

	@Mock
	private DomainDnVerificationInfoHandler mockDnsVerificationInfoHandler;

	private DomainDnsVerificationInfoController tested;

	@Before
	public void setUp() throws Exception {
		tested = new DomainDnsVerificationInfoController(mockDnsVerificationInfoHandler);
	}

	@Test
	public void testREadOwnershipVerificationRecord_whenCalled_thenControllerForwardsItsArgumentsToTheUnderlyingHandler() throws Exception {
		//Given
		String testCustomerId = "testCustomerId";
		String testDomain = "example.com";

		//When
		tested.readOwnershipVerificationRecord(testCustomerId, testDomain);

		//Then
		verify(mockDnsVerificationInfoHandler)
				.readOwnershipVerificationRecords(eq(testCustomerId), eq(testDomain));
	}
}
