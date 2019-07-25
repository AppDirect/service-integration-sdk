package com.appdirect.sdk.meteredUsage;

import com.appdirect.sdk.meteredUsage.model.MeteredUsageRequest;
import com.appdirect.sdk.meteredUsage.model.MeteredUsageRequestAccepted;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MeteredUsageApi {

	@POST("billing/usage")
	Call<MeteredUsageRequestAccepted> meteredUsageCall(@Body MeteredUsageRequest body);
}
