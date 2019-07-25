package com.appdirect.sdk.meteredusage.config;

import java.io.IOException;

import org.springframework.util.StringUtils;

import okhttp3.Interceptor;
import okhttp3.Response;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class ChainableSigningInterceptor extends SigningInterceptor {

	public ChainableSigningInterceptor(OkHttpOAuthConsumer consumer) {
		super(consumer);
	}

	@Override
	public Response intercept(Interceptor.Chain chain) throws IOException {
		if (!StringUtils.hasLength(chain.request().header("Authentication"))) {
			return super.intercept(chain);
		}
		return chain.proceed(chain.request());
	}
}
