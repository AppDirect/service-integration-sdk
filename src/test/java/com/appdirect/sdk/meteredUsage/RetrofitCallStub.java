package com.appdirect.sdk.meteredUsage;

import com.appdirect.sdk.meteredUsage.model.MeteredUsageRequestAccepted;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCallStub {
	private Response<MeteredUsageRequestAccepted> response;
	private Call call;

	public RetrofitCallStub(Response<MeteredUsageRequestAccepted> response) {
		this.response = response;
		this.call = new Call<MeteredUsageRequestAccepted>() {

			@Override
			public Response<MeteredUsageRequestAccepted> execute() {
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
			public Call<MeteredUsageRequestAccepted> clone() {
				return null;
			}
			@Override
			public Request request() {
				return null;
			}
		};
	}

	public Call<MeteredUsageRequestAccepted> getCall() {
		return call;
	}
}
