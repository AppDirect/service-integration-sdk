package com.appdirect.sdk.utils;

import java.time.ZonedDateTime;

import org.apache.commons.lang3.StringUtils;

public class ConstantUtils {
	public static final String BASE_URL = "https://localhost:8080/";
	public static final String CONSUMER_KEY = "ConsumerKey";
	public static final String CONSUMER_SECRET = "ConsumerSecret";
	public static final String IDEMPOTENCY_KEY = "IdempotencyKey";
	public static final boolean BILLABLE = true;
	public static final String REQUEST_ID = "RequestId";

	public static final String ACCOUNT_ID = "AccountId";
	public static final String SUBSCRIPTION_ID = "SubscriptionId";

	public static final String DESCRIPTION = "Description";
	public static final String CUSTOM_UNIT = "PC";

	public static final String ATTRIBUTE_KEY = "AttributeKey";
	public static final String ATTRIBUTE_VALUE = "AttributeValue";

	public static final ZonedDateTime EVENT_DATE = ZonedDateTime.now();
	public static final String EVENT_ID = "EventId";

	public static final String TEST_ENCRYPTION_DATA = "Test Encryption Data";
	public static final String ENCRYPTED_DATA = "3k6xRbfgNTZwz76WFwoBdt/KDX3C0U/yonosIMSvbVg=";

	public static final String EMPTY_SOURCE_TYPE = StringUtils.EMPTY;
	public static final String SOURCE_TYPE = "test-source-type";

	public static final String AUTHORIZATION = "Authorization";
	public static final String AD_AUTHORIZATION_HEADER = "AD-Authorization";
	public static final String BASIC = "Basic ";
	public static final String BEARER = "Bearer ";
	public static final String INTEGRATION_PATH = "/api/v2/integration/**";
	public static final String DOMAIN_ASSOCIATION_PATH = "/api/v2/domainassociation/**";
	public static final String MIGRATION_PATH = "/api/v2/migration/**";
	public static final String RESTRICTIONS_PATH = "/api/v2/restrictions/**";

}
