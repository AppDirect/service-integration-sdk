package com.appdirect.sdk.meteredusage.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	public APIResult reportUsage(String secretKey, String idempotentKey, MeteredUsageItem meteredUsageItem) {
		return reportUsage(secretKey, idempotentKey, Lists.newArrayList(meteredUsageItem));
	}

	@Override
	public APIResult reportUsage(String secretKey, MeteredUsageItem meteredUsageItem) {
		return reportUsage(secretKey, UUID.randomUUID().toString(), Lists.newArrayList(meteredUsageItem));
	}

	@Override
	public APIResult reportUsage(String secretKey, List<MeteredUsageItem> meteredUsageItems) {
		return reportUsage(secretKey, UUID.randomUUID().toString(), meteredUsageItems);
	}

	@Override
	public APIResult reportUsage(String secretKey, String idempotentKey, List<MeteredUsageItem> meteredUsageItems) {

		Preconditions.checkArgument(meteredUsageItems != null && !meteredUsageItems.isEmpty(), "Usage data to report must not be empty");
		Preconditions.checkArgument(!StringUtils.isEmpty(idempotentKey), "IdempotentKey must not be empty");
		Preconditions.checkArgument(!StringUtils.isEmpty(secretKey), "Secret Key must not be empty");

		// Create Request
		MeteredUsageRequest meteredUsageRequest = createMeteredUsageRequest(idempotentKey, meteredUsageItems);

		// Create API
		MeteredUsageApi meteredUsageApi = createMeteredUsageApi(secretKey);

		try {
			return processResponse(meteredUsageApi.meteredUsageCall(meteredUsageRequest).execute());
		} catch (IOException e) {
			log.error("Metered Usage API Client failed with exception={}", e.getMessage(), e);
			throw new MeteredUsageApiException(String.format("Failed to inform Usage with errorCode=%s, message=%s", ErrorCode.UNKNOWN_ERROR, e.getMessage()), e);
		}
	}

	private APIResult processResponse(Response<MeteredUsageResponse> response) {
		if (response.isSuccessful()) {
			return new APIResult(true, response.body().toString());
		}
		log.error("Metered Usage API Client failed with error={}", response.message());
		return new APIResult(false, String.format("Failed to inform Usage with errorCode=%s, message=%s", response.code(), response.message()));
	}

	public MeteredUsageApi createMeteredUsageApi(String key) {
		String secret = credentialsSupplier.getConsumerCredentials(key).developerSecret;
		return oAuth1RetrofitWrapper.sign(key, secret)
				.create(MeteredUsageApi.class);
	}

	private MeteredUsageRequest createMeteredUsageRequest(String idempotentKey, List<MeteredUsageItem> meteredUsageItem) {
		return MeteredUsageRequest.builder()
				.idempotencyKey(idempotentKey)
				.usages(meteredUsageItem)
				.build();
	}
}
