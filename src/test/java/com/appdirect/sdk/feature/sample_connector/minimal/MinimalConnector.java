package com.appdirect.sdk.feature.sample_connector.minimal;

import static com.appdirect.sdk.appmarket.events.APIResult.success;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.appdirect.sdk.ConnectorSdkConfiguration;
import com.appdirect.sdk.appmarket.AppmarketEventHandler;
import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.events.SubscriptionCancel;
import com.appdirect.sdk.appmarket.events.SubscriptionOrder;

/**
 * Sample connector that only supports the mandatory events, not the
 * optional ones.
 */
@SpringBootApplication
@Import(ConnectorSdkConfiguration.class)
public class MinimalConnector {
	@Bean
	public DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier() {
		return someKey -> new Credentials(someKey, "isv-secret");
	}

	@Bean
	public AppmarketEventHandler<SubscriptionOrder> subscriptionOrderHandler() {
		return event -> success("Mandatory SUB_ORDER has been processed");
	}

	@Bean
	public AppmarketEventHandler<SubscriptionCancel> subscriptionCancelHandler() {
		return event -> success("Mandatory SUB_CANCEL has been processed");
	}
}
