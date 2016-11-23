package com.appdirect.sdk.appmarket;

import static com.appdirect.sdk.appmarket.api.ErrorCode.CONFIGURATION_ERROR;
import static com.appdirect.sdk.appmarket.api.EventType.SUBSCRIPTION_CANCEL;
import static com.appdirect.sdk.appmarket.api.EventType.SUBSCRIPTION_CHANGE;
import static com.appdirect.sdk.appmarket.api.EventType.SUBSCRIPTION_NOTICE;
import static com.appdirect.sdk.appmarket.api.EventType.SUBSCRIPTION_ORDER;
import static com.appdirect.sdk.appmarket.api.NoticeType.CLOSED;
import static com.appdirect.sdk.appmarket.api.NoticeType.DEACTIVATED;
import static com.appdirect.sdk.appmarket.api.NoticeType.REACTIVATED;
import static com.appdirect.sdk.appmarket.api.NoticeType.UPCOMING_INVOICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.EventPayload;
import com.appdirect.sdk.appmarket.api.EventType;
import com.appdirect.sdk.appmarket.api.NoticeInfo;
import com.appdirect.sdk.appmarket.api.NoticeType;

@RunWith(MockitoJUnitRunner.class)
public class AppmarketEventDispatcherTest {

	private AppmarketEventDispatcher eventDispatcher;

	@Mock
	private SDKEventHandler mockSubscriptionOrderHandler;
	@Mock
	private SDKEventHandler mockSubscriptionCancelHandler;
	@Mock
	private SDKEventHandler mockSubscriptionChangeHandler;
	@Mock
	private SDKEventHandler mockSubscriptionDeactivatedHandler;
	@Mock
	private SDKEventHandler mockSubscriptionReactivatedhandler;
	@Mock
	private SDKEventHandler mockSubscriptionClosedHandler;
	@Mock
	private SDKEventHandler mockSubscriptionIncomingNoticeHandler;

	@Mock
	private APIResult mockSubscriptionOrderResponse;
	@Mock
	private APIResult mockSubscriptionCancelResponse;
	@Mock
	private APIResult mockSubscriptionChangeResponse;
	@Mock
	private APIResult mockSubscriptionDeactivatedResponse;
	@Mock
	private APIResult mockSubscriptionReactivatedResaponse;
	@Mock
	private APIResult mockSubscriptionClosedResponse;
	@Mock
	private APIResult mockSubscriptionUpcomingInvoiceResponse;

	private String mockRequestConsumerKey = "some-key";

	@Before
	public void setUp() throws Exception {
		eventDispatcher = new AppmarketEventDispatcher(
			mockSubscriptionOrderHandler,
			mockSubscriptionCancelHandler,
			mockSubscriptionChangeHandler,
			mockSubscriptionDeactivatedHandler,
			mockSubscriptionReactivatedhandler,
			mockSubscriptionClosedHandler,
			mockSubscriptionIncomingNoticeHandler
		);

		when(mockSubscriptionOrderHandler.handle(any(), any()))
			.thenReturn(mockSubscriptionOrderResponse);
		when(mockSubscriptionCancelHandler.handle(any(), any()))
			.thenReturn(mockSubscriptionCancelResponse);
		when(mockSubscriptionChangeHandler.handle(any(), any()))
			.thenReturn(mockSubscriptionChangeResponse);
		when(mockSubscriptionDeactivatedHandler.handle(any(), any()))
			.thenReturn(mockSubscriptionDeactivatedResponse);
		when(mockSubscriptionReactivatedhandler.handle(any(), any()))
			.thenReturn(mockSubscriptionReactivatedResaponse);
		when(mockSubscriptionClosedHandler.handle(any(), any()))
			.thenReturn(mockSubscriptionClosedResponse);
		when(mockSubscriptionIncomingNoticeHandler.handle(any(), any()))
			.thenReturn(mockSubscriptionUpcomingInvoiceResponse);
	}

	@Test
	public void testDispatchAndHandle_whenEventTypeNotSupported_returnsConfigError() throws Exception {
		//Given
		EventInfo testEvent = EventInfo.builder().type(EventType.USER_LINK).build();

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(mockRequestConsumerKey, testEvent);

		//Then
		assertThat(result.getErrorCode()).isEqualTo(CONFIGURATION_ERROR);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsSusbscriptionOrder_thenInvokeAppropriateHandler() throws Exception {
		//Given
		EventInfo testEvent = someSubOrderEvent();

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(mockRequestConsumerKey, testEvent);

		//Then
		assertThat(result).isEqualTo(mockSubscriptionOrderResponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsSusbscriptionCancel_thenInvokeAppropriateHandler() throws Exception {
		//Given
		EventInfo testEvent = someSubCancelEvent();

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(mockRequestConsumerKey, testEvent);

		//Then
		assertThat(result).isEqualTo(mockSubscriptionCancelResponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsSusbscriptionChange_thenInvokeAppropriateHandler() throws Exception {
		//Given
		EventInfo testEvent = someSubChange();

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(mockRequestConsumerKey, testEvent);

		//Then
		assertThat(result).isEqualTo(mockSubscriptionChangeResponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsSusbscriptionDeactivate_thenInvokeAppropriateHandler() throws Exception {
		//Given
		EventInfo testEvent = subscriptionNoticeOfType(DEACTIVATED);

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(mockRequestConsumerKey, testEvent);

		//Then
		assertThat(result).isEqualTo(mockSubscriptionDeactivatedResponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsSusbscriptionReactivate_thenInvokeAppropriateHandler() throws Exception {
		//Given
		EventInfo testEvent = subscriptionNoticeOfType(REACTIVATED);

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(mockRequestConsumerKey, testEvent);

		//Then
		assertThat(result).isEqualTo(mockSubscriptionReactivatedResaponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsSusbscriptionClose_thenInvokeAppropriateHandler() throws Exception {
		//Given
		EventInfo testEvent = subscriptionNoticeOfType(CLOSED);

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(mockRequestConsumerKey, testEvent);

		//Then
		assertThat(result).isEqualTo(mockSubscriptionClosedResponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsSusbscriptionUpcomingInvoice_thenInvokeAppropriateHandler() throws Exception {
		//Given
		EventInfo testEvent = subscriptionNoticeOfType(UPCOMING_INVOICE);

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(mockRequestConsumerKey, testEvent);

		//Then
		assertThat(result).isEqualTo(mockSubscriptionUpcomingInvoiceResponse);
	}

	private EventInfo subscriptionNoticeOfType(NoticeType type) {
		return EventInfo.builder()
			.type(SUBSCRIPTION_NOTICE)
			.payload(EventPayload.builder()
				.notice(new NoticeInfo(type, "testEvent"))
				.build())
			.build();
	}

	private EventInfo someSubChange() {
		return EventInfo.builder().type(SUBSCRIPTION_CHANGE).build();
	}

	private EventInfo someSubOrderEvent() {
		return EventInfo.builder().type(SUBSCRIPTION_ORDER).build();
	}

	private EventInfo someSubCancelEvent() {
		return EventInfo.builder().type(SUBSCRIPTION_CANCEL).build();
	}
}
