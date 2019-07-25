package com.appdirect.sdk.meteredusage;

import com.appdirect.sdk.meteredusage.model.MeteredUsageRequest;
import com.appdirect.sdk.meteredusage.model.MeteredUsageResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MeteredUsageApi {

	@POST("billing/usage")
	Call<MeteredUsageResponse> meteredUsageCall(@Body MeteredUsageRequest body);
}
