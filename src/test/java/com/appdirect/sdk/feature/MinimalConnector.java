package com.appdirect.sdk.feature;

import static com.appdirect.sdk.appmarket.api.APIResult.success;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.appdirect.sdk.ConnectorSdkConfiguration;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.alt.DeveloperEventHandler;
import com.appdirect.sdk.appmarket.api.SubscriptionCancel;
import com.appdirect.sdk.appmarket.api.SubscriptionOrder;

@SpringBootApplication
@Import(ConnectorSdkConfiguration.class)
public class MinimalConnector {
	@Bean
	public DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier() {
		return () -> new DeveloperSpecificAppmarketCredentials("isv-key", "isv-secret");
	}

	@Bean
	public DeveloperEventHandler<SubscriptionOrder> subscriptionOrderHandler() {
		return event -> success("SUB_ORDER has been processed, trust me.");
	}

	@Bean
	public DeveloperEventHandler<SubscriptionCancel> subscriptionCancelHandler() {
		return event -> success("SUB_CANCEL has been processed, for real.");
	}
}
