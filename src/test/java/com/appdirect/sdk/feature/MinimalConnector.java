package com.appdirect.sdk.feature;

import static com.appdirect.sdk.appmarket.events.APIResult.success;
import static com.appdirect.sdk.appmarket.events.ErrorCode.USER_NOT_FOUND;
import static java.lang.String.format;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

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
import com.appdirect.sdk.appmarket.events.UserAssignment;
import com.appdirect.sdk.appmarket.events.UserUnassignment;
import com.appdirect.sdk.exception.DeveloperServiceException;

@SpringBootApplication
@EnableAutoConfiguration
@Import(ConnectorSdkConfiguration.class)
public class MinimalConnector {
	@Bean
	public DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier() {
		return someKey -> new Credentials(someKey, "isv-secret");
	}

	@Bean
	public AppmarketEventHandler<SubscriptionOrder> subscriptionOrderHandler() {
		return event -> {
			if (event.getPurchaserInfo() == null)
				throw new DeveloperServiceException(USER_NOT_FOUND, "You should always have a creator");
			return success("SUB_ORDER has been processed, trust me.");
		};
	}

	@Bean
	public AppmarketEventHandler<SubscriptionCancel> subscriptionCancelHandler() {
		return event -> {
			sleepForOneSecond_toTriggerRaceCondition_thatModifiedSharedQueryParams();
			String[] queryParamValue = event.getQueryParameters().getOrDefault("query", new String[]{"absent!"});
			return success(format("SUB_CANCEL %s has been processed, for real. query=%s", event.getAccountIdentifier(), queryParamValue[0]));
		};
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
	public AppmarketEventHandler<UserAssignment> userAssignmentDevHandler() {
		return event -> success(
			format("USER_ASSIGN for user %s for account %s has been processed, for real.", event.getAssignedUserId(), event.getAccountId())
		);
	}

	@Bean
	public AppmarketEventHandler<UserUnassignment> userUnassignmentDevHandler() {
		return event -> success(
			format("USER_UNASSIGN for user %s for account %s has been processed, for real.", event.getUnassignedUsedId(), event.getAccountId())
		);
	}

	private void sleepForOneSecond_toTriggerRaceCondition_thatModifiedSharedQueryParams() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
