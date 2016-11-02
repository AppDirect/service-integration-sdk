package com.appdirect.sdk.feature;

import static com.appdirect.sdk.appmarket.api.APIResult.success;
import static java.lang.String.format;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.appdirect.sdk.ConnectorSdkConfiguration;
import com.appdirect.sdk.appmarket.AppmarketEventHandler;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
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
	public AppmarketEventHandler<SubscriptionOrder> subscriptionOrderHandler() {
		return event -> success("SUB_ORDER has been processed, trust me.");
	}

	@Bean
	public AppmarketEventHandler<SubscriptionCancel> subscriptionCancelHandler() {
		return event -> success(
			format("SUB_CANCEL %s has been processed, for real.", event.getAccountIdentifier())
		);
	}
}
