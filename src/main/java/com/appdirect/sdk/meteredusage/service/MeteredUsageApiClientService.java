package com.appdirect.sdk.meteredusage.service;

import java.util.List;

import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.appmarket.events.AppmarketBillingClient;
import com.appdirect.sdk.meteredusage.exception.MeteredUsageApiException;
import com.appdirect.sdk.meteredusage.model.MeteredUsageItem;

public interface MeteredUsageApiClientService {

	/**
	 * Calls the Metered Usage API Endpoint to bill usages.
	 * This should be used instead of {@link AppmarketBillingClient} as this replaces the old API version.
	 *
	 * @param baseUrl          from the request
	 * @param secretKey        to sign the request
	 * @param idempotentKey    to make unique calls
	 * @param meteredUsageItem usage instance to be reported
	 * @param billable         specifies if the usage to be reported is billable or estimated
	 * @return an {@link APIResult} instance representing the marketplace response
	 * <p>
	 * throws an {@link MeteredUsageApiException} to the client with an error code and a status:
	 */
	APIResult reportUsage(String baseUrl, String secretKey, String idempotentKey, MeteredUsageItem meteredUsageItem, boolean billable);

	/**
	 * Calls the Metered Usage API Endpoint to bill usages.
	 * This should be used instead of {@link AppmarketBillingClient} as this replaces the old API version.
	 *
	 * @param baseUrl          from the request
	 * @param secretKey        to sign the request
	 * @param meteredUsageItem usage instance to be reported
	 * @param billable         specifies if the usage to be reported is billable or estimated
	 * @return an {@link APIResult} instance representing the marketplace response
	 * <p>
	 * throws an {@link MeteredUsageApiException} to the client with an error code and a status:
	 */
	APIResult reportUsage(String baseUrl, String secretKey, MeteredUsageItem meteredUsageItem, boolean billable);

	/**
	 * Calls the Metered Usage API Endpoint to bill usages.
	 * This should be used instead of {@link AppmarketBillingClient} as this replaces the old API version.
	 *
	 * @param baseUrl           from the request
	 * @param secretKey         to sign the request
	 * @param meteredUsageItems list of usages to be reported
	 * @param billable          specifies if the usage to be reported is billable or estimated
	 * @return an {@link APIResult} instance representing the marketplace response
	 * <p>
	 * throws an {@link MeteredUsageApiException} to the client with an error code and a status:
	 */
	APIResult reportUsage(String baseUrl, String secretKey, List<MeteredUsageItem> meteredUsageItems, boolean billable);

	/**
	 * Calls the Metered Usage API Endpoint to bill usages.
	 * This should be used instead of {@link AppmarketBillingClient} as this replaces the old API version.
	 *
	 * @param baseUrl           from the request
	 * @param secretKey         to sign the request
	 * @param idempotentKey     to make unique calls
	 * @param meteredUsageItems list of usages to be reported
	 * @param billable          specifies if the usage to be reported is billable or estimated
	 * @return an {@link APIResult} instance representing the marketplace response
	 * <p>
	 * throws an {@link MeteredUsageApiException} to the client with an error code and a status:
	 */
	APIResult reportUsage(String baseUrl, String secretKey, String idempotentKey, List<MeteredUsageItem> meteredUsageItems, boolean billable);

	/**
	 * Calls the Metered Usage API Endpoint to bill usages.
	 * This should be used instead of {@link AppmarketBillingClient} as this replaces the old API version.
	 *
	 * @param baseUrl          from the request
	 * @param secretKey        to sign the request
	 * @param idempotentKey    to make unique calls
	 * @param meteredUsageItem usage instance to be reported
	 * @param billable         specifies if the usage to be reported is billable or estimated
	 * @return an {@link APIResult} instance representing the marketplace response
	 * <p>
	 * throws an {@link MeteredUsageApiException} to the client with an error code and a status:
	 */
	APIResult reportUsage(String baseUrl, String idempotentKey, MeteredUsageItem meteredUsageItem, boolean billable, String secretKey, String secret);

	/**
	 * Calls the Metered Usage API Endpoint to bill usages.
	 * This should be used instead of {@link AppmarketBillingClient} as this replaces the old API version.
	 *
	 * @param baseUrl          from the request
	 * @param secretKey        to sign the request
	 * @param meteredUsageItem usage instance to be reported
	 * @param billable         specifies if the usage to be reported is billable or estimated
	 * @return an {@link APIResult} instance representing the marketplace response
	 * <p>
	 * throws an {@link MeteredUsageApiException} to the client with an error code and a status:
	 */
	APIResult reportUsage(String baseUrl, MeteredUsageItem meteredUsageItem, boolean billable, String secretKey, String secret);

	/**
	 * Calls the Metered Usage API Endpoint to bill usages.
	 * This should be used instead of {@link AppmarketBillingClient} as this replaces the old API version.
	 *
	 * @param baseUrl           from the request
	 * @param secretKey         to sign the request
	 * @param meteredUsageItems list of usages to be reported
	 * @param billable          specifies if the usage to be reported is billable or estimated
	 * @return an {@link APIResult} instance representing the marketplace response
	 * <p>
	 * throws an {@link MeteredUsageApiException} to the client with an error code and a status:
	 */
	APIResult reportUsage(String baseUrl, List<MeteredUsageItem> meteredUsageItems, boolean billable, String secretKey, String secret);

	/**
	 * Calls the Metered Usage API Endpoint to bill usages.
	 * This should be used instead of {@link AppmarketBillingClient} as this replaces the old API version.
	 *
	 * @param baseUrl           from the request
	 * @param secretKey         to sign the request
	 * @param idempotentKey     to make unique calls
	 * @param meteredUsageItems list of usages to be reported
	 * @param billable          specifies if the usage to be reported is billable or estimated
	 * @return an {@link APIResult} instance representing the marketplace response
	 * <p>
	 * throws an {@link MeteredUsageApiException} to the client with an error code and a status:
	 */
	APIResult reportUsage(String baseUrl, String idempotentKey, List<MeteredUsageItem> meteredUsageItems, boolean billable, String secretKey, String secret);

}
