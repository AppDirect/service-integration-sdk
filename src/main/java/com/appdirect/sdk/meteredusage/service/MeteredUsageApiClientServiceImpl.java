package com.appdirect.sdk.meteredusage.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.appmarket.events.ErrorCode;
import com.appdirect.sdk.meteredusage.MeteredUsageApi;
import com.appdirect.sdk.meteredusage.config.OAuth1RetrofitWrapper;
import com.appdirect.sdk.meteredusage.exception.MeteredUsageApiException;
import com.appdirect.sdk.meteredusage.model.MeteredUsageItem;
import com.appdirect.sdk.meteredusage.model.MeteredUsageRequest;
import com.appdirect.sdk.meteredusage.model.MeteredUsageResponse;
import com.google.common.annotations.VisibleForTesting;
import com.google.gdata.util.common.base.Preconditions;
import com.google.inject.internal.Lists;
import retrofit2.Response;

@Slf4j
@Service
public class MeteredUsageApiClientServiceImpl implements MeteredUsageApiClientService {

	private final DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;
	private final OAuth1RetrofitWrapper oAuth1RetrofitWrapper;

	@Autowired
	public MeteredUsageApiClientServiceImpl(DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier, OAuth1RetrofitWrapper oAuth1RetrofitWrapper) {
		this.credentialsSupplier = credentialsSupplier;
		this.oAuth1RetrofitWrapper = oAuth1RetrofitWrapper;
	}

	@Override
	public APIResult reportUsage(String baseUrl, String secretKey, String idempotentKey, MeteredUsageItem meteredUsageItem, boolean billable) {
		return reportUsage(baseUrl, secretKey, idempotentKey, Lists.newArrayList(meteredUsageItem), billable);
	}

	@Override
	public APIResult reportUsage(String baseUrl, String secretKey, MeteredUsageItem meteredUsageItem, boolean billable) {
		return reportUsage(baseUrl, secretKey, UUID.randomUUID().toString(), Lists.newArrayList(meteredUsageItem), billable);
	}

	@Override
	public APIResult reportUsage(String baseUrl, String secretKey, List<MeteredUsageItem> meteredUsageItems, boolean billable) {
		return reportUsage(baseUrl, secretKey, UUID.randomUUID().toString(), meteredUsageItems, billable);
	}

	@Override
	public APIResult reportUsage(String baseUrl, String secretKey, String idempotentKey, List<MeteredUsageItem> meteredUsageItems, boolean billable) {
		return reportUsage(baseUrl, secretKey, UUID.randomUUID().toString(), meteredUsageItems, billable, null);
	}

	@Override
	public APIResult reportUsage(String baseUrl, String secretKey, String idempotentKey, MeteredUsageItem meteredUsageItem, boolean billable, String secret) {
		return reportUsage(baseUrl, secretKey, idempotentKey, Lists.newArrayList(meteredUsageItem), billable, secret);
	}

	@Override
	public APIResult reportUsage(String baseUrl, String secretKey, MeteredUsageItem meteredUsageItem, boolean billable, String secret) {
		return reportUsage(baseUrl, secretKey, UUID.randomUUID().toString(), Lists.newArrayList(meteredUsageItem), billable, secret);
	}

	@Override
	public APIResult reportUsage(String baseUrl, String secretKey, List<MeteredUsageItem> meteredUsageItems, boolean billable, String secret) {
		return reportUsage(baseUrl, secretKey, UUID.randomUUID().toString(), meteredUsageItems, billable, secret);
	}

	@Override
	public APIResult reportUsage(String baseUrl, String secretKey, String idempotentKey, List<MeteredUsageItem> meteredUsageItems, boolean billable, String secret) {
		Preconditions.checkArgument(!StringUtils.isEmpty(baseUrl), "Base URL must not be empty");
		Preconditions.checkArgument(!StringUtils.isEmpty(secretKey), "Secret Key must not be empty");
		Preconditions.checkArgument(!StringUtils.isEmpty(idempotentKey), "IdempotentKey must not be empty");
		Preconditions.checkArgument(!CollectionUtils.isEmpty(meteredUsageItems), "Usage data to report must not be empty");

		// Create Request
		MeteredUsageRequest meteredUsageRequest = createMeteredUsageRequest(idempotentKey, meteredUsageItems, billable);

		// Create API
		MeteredUsageApi meteredUsageApi = createMeteredUsageApi(baseUrl, secretKey, secret);

		try {
			return processResponse(meteredUsageApi.meteredUsageCall(meteredUsageRequest).execute());
		} catch (IOException e) {
			log.error("Metered Usage API Client failed with exception={}", e.getMessage(), e);
			throw new MeteredUsageApiException(String.format("Failed to inform Usage with errorCode=%s, message=%s", ErrorCode.UNKNOWN_ERROR, e.getMessage()), e);
		}
	}

	@VisibleForTesting
	MeteredUsageApi createMeteredUsageApi(String baseUrl, String secretKey, String secret) {
		if (StringUtils.isEmpty(secret)) {
			secret = credentialsSupplier.getConsumerCredentials(secretKey).developerSecret;
		}
		return oAuth1RetrofitWrapper
			.baseUrl(baseUrl)
			.sign(secretKey, secret)
			.build()
			.create(MeteredUsageApi.class);
	}

	private APIResult processResponse(Response<MeteredUsageResponse> response) {
		if (response.isSuccessful()) {
			return new APIResult(true, response.body().toString());
		}
		log.error("Metered Usage API Client failed with error={}", response.message());
		return new APIResult(false, String.format("Failed to inform Usage with errorCode=%s, message=%s", response.code(), response.message()));
	}

	private MeteredUsageRequest createMeteredUsageRequest(String idempotentKey, List<MeteredUsageItem> meteredUsageItem, boolean billable) {
		return MeteredUsageRequest.builder()
			.idempotencyKey(idempotentKey)
			.billable(billable)
			.usages(meteredUsageItem)
			.build();
	}
}
