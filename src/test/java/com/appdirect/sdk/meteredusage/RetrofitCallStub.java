package com.appdirect.sdk.meteredusage;

import com.appdirect.sdk.meteredusage.model.MeteredUsageResponse;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCallStub {
	private Response<MeteredUsageResponse> response;
	private Call call;

	public RetrofitCallStub(Response<MeteredUsageResponse> response) {
		this.response = response;
		this.call = new Call<MeteredUsageResponse>() {

			@Override
			public Response<MeteredUsageResponse> execute() {
				return response;
			}

			@Override
			public void enqueue(Callback callback) {

			}

			@Override
			public boolean isExecuted() {
				return false;
			}
			@Override
			public void cancel() {

			}
			@Override
			public boolean isCanceled() {
				return false;
			}
			@Override
			public Call<MeteredUsageResponse> clone() {
				return null;
			}
			@Override
			public Request request() {
				return null;
			}
		};
	}

	public Call<MeteredUsageResponse> getCall() {
		return call;
	}
}
