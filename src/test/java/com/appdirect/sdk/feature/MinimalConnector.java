package com.appdirect.sdk.feature;

import static com.appdirect.sdk.appmarket.events.APIResult.success;
import static java.lang.String.format;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.appdirect.sdk.ConnectorSdkConfiguration;
import com.appdirect.sdk.appmarket.AppmarketEventHandler;
import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.events.SubscriptionCancel;
import com.appdirect.sdk.appmarket.events.SubscriptionChange;
import com.appdirect.sdk.appmarket.events.SubscriptionClosed;
import com.appdirect.sdk.appmarket.events.SubscriptionDeactivated;
import com.appdirect.sdk.appmarket.events.SubscriptionOrder;
import com.appdirect.sdk.appmarket.events.SubscriptionReactivated;
import com.appdirect.sdk.appmarket.events.SubscriptionUpcomingInvoice;

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

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost("127.0.0.1");
		javaMailSender.setPort(999);
		return javaMailSender;
	}
}
