package com.appdirect.sdk.appmarket.restrictions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Locale;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.appdirect.sdk.appmarket.restrictions.context.RestrictionInfo;

@RunWith(MockitoJUnitRunner.class)
public class RestrictionInfoTest {
	private RestrictionInfo restrictionInfo;

	@Before
	public void setup() {
		String applicationUuid = UUID.randomUUID().toString();
		String companyUuid = UUID.randomUUID().toString();
		Locale locale = Locale.US;

		restrictionInfo = RestrictionInfo.builder()
			.editionInfo(Collections.emptyList())
			.userInfo(Collections.emptyList())
			.applicationUuid(applicationUuid)
			.companyUuid(companyUuid)
			.locale(locale.toLanguageTag()).build();
	}

	@Test
	public void testInitialOrder_MissingExternalVendorId_SubscriptionOrder() {
		restrictionInfo.setOperation(OperationType.SUBSCRIPTION_ORDER);

		boolean result = restrictionInfo.isInitialOrder();
		assertThat(result).isTrue();
	}

	@Test
	public void testInitialOrder_ExternalVendorId_SubscriptionOrder() {
		restrictionInfo.setExternalVendorId("187401947440");
		restrictionInfo.setOperation(OperationType.SUBSCRIPTION_ORDER);

		boolean result = restrictionInfo.isInitialOrder();
		assertThat(result).isFalse();
	}

	@Test
	public void testInitialOrder_MissingExternalVendorId_SubscriptionChange() {
		restrictionInfo.setOperation(OperationType.SUBSCRIPTION_CHANGE);

		boolean result = restrictionInfo.isInitialOrder();
		assertThat(result).isFalse();
	}

	@Test
	public void testInitialOrder_ExternalVendorId_SubscriptionChange() {
		restrictionInfo.setExternalVendorId("187401947448");
		restrictionInfo.setOperation(OperationType.SUBSCRIPTION_CHANGE);

		boolean result = restrictionInfo.isInitialOrder();
		assertThat(result).isFalse();
	}
}
