package com.appdirect.sdk.feature.sample_connector.full;

import static com.appdirect.sdk.appmarket.events.APIResult.failure;
import static com.appdirect.sdk.appmarket.events.APIResult.success;
import static com.appdirect.sdk.appmarket.events.ErrorCode.OPERATION_CANCELLED;
import static com.appdirect.sdk.appmarket.events.ErrorCode.USER_NOT_FOUND;
import static java.lang.String.format;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import com.appdirect.sdk.ConnectorSdkConfiguration;
import com.appdirect.sdk.appmarket.AppmarketEventHandler;
import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.events.AddonSubscriptionCancel;
import com.appdirect.sdk.appmarket.events.AddonSubscriptionOrder;
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

/**
 * Sample connector that supports all of the supported events, both the
 * mandatory and optional ones.
 */
@SpringBootApplication
@Import(ConnectorSdkConfiguration.class)
public class FullConnector {
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

	@Primary
	@Bean
	public AppmarketEventHandler<SubscriptionChange> mySubscriptionChangeHandler() {
		return event -> success(
			format("SUB_CHANGE for accountId=%s has been processed, %dGB has been requested.", event.getAccount().getAccountIdentifier(), event.getOrder().getItems().get(0).getQuantity())
		);
	}

	@Primary
	@Bean
	public AppmarketEventHandler<SubscriptionClosed> mySubscriptionClosedHandler() {
		return event -> {
			boolean callShouldFail = event.getQueryParameters().containsKey("failThisCall");
			return callShouldFail ?
					failure(OPERATION_CANCELLED, "You made this call fail") :
					success(format("SUB_CLOSED %s has been processed, for real.", event.getAccountInfo().getAccountIdentifier()));
		};
	}

	@Primary
	@Bean
	public AppmarketEventHandler<SubscriptionDeactivated> mySubscriptionDeactivatedHandler() {
		return event -> success(
			format("SUB_DEACTIVATED %s has been processed, for real.", event.getAccountInfo().getAccountIdentifier())
		);
	}

	@Primary
	@Bean
	public AppmarketEventHandler<SubscriptionReactivated> mySubscriptionReactivatedHandler() {
		return event -> success(
			format("SUB_REACTIVATED %s has been processed, for real.", event.getAccountInfo().getAccountIdentifier())
		);
	}

	@Primary
	@Bean
	public AppmarketEventHandler<SubscriptionUpcomingInvoice> mySubscriptionUpcomingNoticeHandler() {
		return event -> success(
			format("SUB_INVOICE %s has been processed, for real.", event.getAccountInfo().getAccountIdentifier())
		);
	}

	@Primary
	@Bean
	public AppmarketEventHandler<AddonSubscriptionOrder> myAddonSubscriptionOrderHandler() {
		return event -> success("ADDON_ORDER has been processed just now.");
	}

	@Primary
	@Bean
	public AppmarketEventHandler<AddonSubscriptionCancel> myAddonSubscriptionCancelAppmarketEventHandler() {
		return event -> success("ADDON_CANCEL has been processed just now.");
	}

	@Primary
	@Bean
	public AppmarketEventHandler<UserAssignment> myUserAssignmentDevHandler() {
		return event -> success(
			format("USER_ASSIGN for user %s for account %s has been processed, for real.", event.getAssignedUserId(), event.getAccountId())
		);
	}

	@Primary
	@Bean
	public AppmarketEventHandler<UserUnassignment> myUserUnassignmentDevHandler() {
		return event -> success(
			format("USER_UNASSIGN for user %s for account %s has been processed, for real.", event.getUnassignedUserId(), event.getAccountId())
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
