package com.appdirect.sdk.meteredUsage.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.meteredUsage.MeteredUsageApi;
import com.appdirect.sdk.meteredUsage.config.MeteredUsageRetrofitConfiguration;
import com.appdirect.sdk.meteredUsage.exception.APIErrorCode;
import com.appdirect.sdk.meteredUsage.exception.MeteredUsageApiException;
import com.appdirect.sdk.meteredUsage.model.MeteredUsageItem;
import com.appdirect.sdk.meteredUsage.model.MeteredUsageRequest;
import com.appdirect.sdk.meteredUsage.model.MeteredUsageRequestAccepted;
import com.google.gdata.util.common.base.Preconditions;
import com.google.inject.internal.Lists;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

@Slf4j
@Service
public class MeteredUsageApiClientServiceImpl implements MeteredUsageApiClientService {

	private final Retrofit.Builder meteredUsageRetrofitBuilder;
	private final DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;

	@Autowired
	public MeteredUsageApiClientServiceImpl(@Qualifier("meteredUsageRetrofitBuilder") Retrofit.Builder meteredUsageRetrofitBuilder, DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier) {
		this.meteredUsageRetrofitBuilder = meteredUsageRetrofitBuilder;
		this.credentialsSupplier = credentialsSupplier;
	}

	@Override
	public APIResult reportUsage(String baseUrl, String key, String idempotentKey, MeteredUsageItem meteredUsageItem) {
		return reportUsage(baseUrl, key, idempotentKey, Lists.newArrayList(meteredUsageItem));
	}

	@Override
	public APIResult reportUsage(String baseUrl, String secretKey, String idempotentKey, List<MeteredUsageItem> meteredUsageItem) {

		Preconditions.checkArgument(meteredUsageItem != null && !meteredUsageItem.isEmpty(), "Usage data to report must not be null");
		Preconditions.checkArgument(secretKey != null && !secretKey.equals(""), "Key must not be null");
		Preconditions.checkArgument(baseUrl != null && !baseUrl.equals(""), "API Base URL must not be empty");

		// Create Request
		MeteredUsageRequest meteredUsageRequest = createMeteredUsageRequest(idempotentKey, meteredUsageItem);

		// Create API
		MeteredUsageApi meteredUsageApi = createMeteredUsageApi(baseUrl, secretKey);

		try {
			Call<MeteredUsageRequestAccepted> call = meteredUsageApi.meteredUsageCall(meteredUsageRequest);
			Response<MeteredUsageRequestAccepted> response = call.execute();
			return processResponse(response);
		} catch (IOException e) {
			log.error("Metered Usage API Client failed with exception={}", e.getMessage(), e);
			throw new MeteredUsageApiException(String.format("Failed to inform Usage with errorCode=%s, message=%s", APIErrorCode.UNKNOWN.getStatusCode(), APIErrorCode.UNKNOWN.getDescription()), e);
		}
	}

	private APIResult processResponse(Response<MeteredUsageRequestAccepted> response) {
		if (response.isSuccessful()) {
			return new APIResult(true, response.body().toString());
		}
		try {
			log.error("Metered Usage API Client failed with error={}", response.message());
			APIErrorCode error = APIErrorCode.getByDescriptionAndStatusCode(response.code());
			return new APIResult(false, String.format("Failed to inform Usage with errorCode=%s, message=%s", error.getStatusCode(), error.getDescription()));
		} catch (Exception e) {
			log.error("Metered Usage API Client failed with error={}", e.getMessage(), e);
			return new APIResult(false, String.format("Failed to inform Usage with message=%s", e.getMessage()));
		}
	}

	public MeteredUsageApi createMeteredUsageApi(String baseUrl, String key) {
		String secret = credentialsSupplier.getConsumerCredentials(key).developerSecret;

		return meteredUsageRetrofitBuilder
				.baseUrl(baseUrl)
				.client(MeteredUsageRetrofitConfiguration.getOkHttpOAuthConsumer(key, secret))
				.build()
				.create(MeteredUsageApi.class);
	}

	private MeteredUsageRequest createMeteredUsageRequest(String idempotentKey, List<MeteredUsageItem> meteredUsageItem){
		MeteredUsageRequest meteredUsageRequest = new MeteredUsageRequest();

		if (idempotentKey == null || idempotentKey.equals("")) {
			idempotentKey = UUID.randomUUID().toString();
		}

		meteredUsageRequest.setIdempotencyKey(idempotentKey);
		meteredUsageRequest.setUsages(meteredUsageItem);

		return meteredUsageRequest;
	}
}
