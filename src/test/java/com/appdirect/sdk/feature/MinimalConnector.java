package com.appdirect.sdk.feature;

import static com.appdirect.sdk.appmarket.api.APIResult.success;
import static java.lang.String.format;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.appdirect.sdk.ConnectorSdkConfiguration;
import com.appdirect.sdk.appmarket.AppmarketEventHandler;
import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.api.SubscriptionCancel;
import com.appdirect.sdk.appmarket.api.SubscriptionChange;
import com.appdirect.sdk.appmarket.api.SubscriptionClosed;
import com.appdirect.sdk.appmarket.api.SubscriptionDeactivated;
import com.appdirect.sdk.appmarket.api.SubscriptionOrder;
import com.appdirect.sdk.appmarket.api.SubscriptionReactivated;
import com.appdirect.sdk.appmarket.api.SubscriptionUpcomingInvoice;

@SpringBootApplication
@Import(ConnectorSdkConfiguration.class)
public class MinimalConnector {
	@Bean
	public DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier() {
		return someKey -> new Credentials(someKey, "isv-secret");
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

	@Bean
	public AppmarketEventHandler<SubscriptionChange> subscriptionChangeHandler() {
		return event -> success(
				format("SUB_CHANGE for accountId=%s has been processed, %dGB has been requested.", event.getAccount().getAccountIdentifier(), event.getOrder().getItems().get(0).getQuantity())
		);
	}

	@Bean
	public AppmarketEventHandler<SubscriptionClosed> subscriptionClosedHandler() {
		return event -> success(
			format("SUB_CLOSED %s has been processed, for real.", event.getAccountInfo().getAccountIdentifier())
		);
	}

	@Bean
	public AppmarketEventHandler<SubscriptionDeactivated> subscriptionDeactivatedHandler() {
		return event -> success(
			format("SUB_DEACTIVATED %s has been processed, for real.", event.getAccountInfo().getAccountIdentifier())
		);
	}

	@Bean
	public AppmarketEventHandler<SubscriptionReactivated> subscriptionReactivatedHandler() {
		return event -> success(
			format("SUB_REACTIVATED %s has been processed, for real.", event.getAccountInfo().getAccountIdentifier())
		);
	}

	@Bean
	public AppmarketEventHandler<SubscriptionUpcomingInvoice> subscriptionUpcomingNoticeHandler() {
		return event -> success(
			format("SUB_INVOICE %s has been processed, for real.", event.getAccountInfo().getAccountIdentifier())
		);
	}
}
