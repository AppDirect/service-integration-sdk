/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.APIResult.success;
import static com.appdirect.sdk.appmarket.events.EventHandlingContexts.defaultEventContext;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_CANCEL;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_CHANGE;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_NOTICE;
import static com.appdirect.sdk.appmarket.events.EventType.SUBSCRIPTION_ORDER;
import static com.appdirect.sdk.appmarket.events.EventType.USER_ASSIGNMENT;
import static com.appdirect.sdk.appmarket.events.EventType.USER_UNASSIGNMENT;
import static com.appdirect.sdk.appmarket.events.NoticeType.CLOSED;
import static com.appdirect.sdk.appmarket.events.NoticeType.DEACTIVATED;
import static com.appdirect.sdk.appmarket.events.NoticeType.REACTIVATED;
import static com.appdirect.sdk.appmarket.events.NoticeType.UPCOMING_INVOICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AppmarketEventDispatcherTest {
	private AppmarketEventDispatcher eventDispatcher;

	@Mock
	private Events mockEvents;
	@Mock
	private AsyncEventHandler mockAsyncEventHandler;

	@Mock
	private SDKEventHandler mockSubscriptionOrderHandler;
	@Mock
	private SDKEventHandler mockSubscriptionCancelHandler;
	@Mock
	private SDKEventHandler mockSubscriptionChangeHandler;
	@Mock
	private SDKEventHandler mockSubscriptionDeactivatedHandler;
	@Mock
	private SDKEventHandler mockSubscriptionReactivatedHandler;
	@Mock
	private SDKEventHandler mockSubscriptionClosedHandler;
	@Mock
	private SDKEventHandler mockSubscriptionIncomingNoticeHandler;
	@Mock
	private SDKEventHandler mockAddonSubscriptionOrderHandler;
	@Mock
	private SDKEventHandler mockAddonSubscriptionCancelHandler;
	@Mock
	private SDKEventHandler mockUnknownEventHandler;

	@Mock
	private APIResult mockSubscriptionOrderResponse;
	@Mock
	private APIResult mockSubscriptionCancelResponse;
	@Mock
	private APIResult mockSubscriptionChangeResponse;
	@Mock
	private APIResult mockSubscriptionDeactivatedResponse;
	@Mock
	private APIResult mockSubscriptionReactivatedResponse;
	@Mock
	private APIResult mockSubscriptionClosedResponse;
	@Mock
	private APIResult mockSubscriptionUpcomingInvoiceResponse;
	@Mock
	private APIResult mockAddonSubscriptionOrderResponse;
	@Mock
	private APIResult mockUnknownEventResponse;
	@Mock
	private SDKEventHandler mockUserAssignmentHandler;
	@Mock
	private SDKEventHandler mockUserUnassignmentHandler;
	@Mock
	private APIResult mockUserAssignmentResponse;
	@Mock
	private APIResult mockUserUnassignmentResponse;
	@Mock
	private APIResult mockAddonSubscriptionCancelResponse;

	@Mock
	private AddonEventDetector mockAddonDetector;

	@Before
	public void setUp() throws Exception {
		eventDispatcher = new AppmarketEventDispatcher(
			mockEvents,
			mockAsyncEventHandler,
			mockSubscriptionOrderHandler,
			mockSubscriptionCancelHandler,
			mockSubscriptionChangeHandler,
			mockSubscriptionDeactivatedHandler,
				mockSubscriptionReactivatedHandler,
			mockSubscriptionClosedHandler,
			mockSubscriptionIncomingNoticeHandler,
			mockAddonSubscriptionOrderHandler,
			mockAddonSubscriptionCancelHandler,
			mockUserAssignmentHandler,
			mockUserUnassignmentHandler,
			mockUnknownEventHandler,
			mockAddonDetector
		);

		when(mockEvents.eventShouldBeHandledAsync(any()))
			.thenReturn(false);

		when(mockSubscriptionOrderHandler.handle(any(), any()))
			.thenReturn(mockSubscriptionOrderResponse);
		when(mockSubscriptionCancelHandler.handle(any(), any()))
			.thenReturn(mockSubscriptionCancelResponse);
		when(mockSubscriptionChangeHandler.handle(any(), any()))
			.thenReturn(mockSubscriptionChangeResponse);
		when(mockSubscriptionDeactivatedHandler.handle(any(), any()))
			.thenReturn(mockSubscriptionDeactivatedResponse);
		when(mockSubscriptionReactivatedHandler.handle(any(), any()))
			.thenReturn(mockSubscriptionReactivatedResponse);
		when(mockSubscriptionClosedHandler.handle(any(), any()))
			.thenReturn(mockSubscriptionClosedResponse);
		when(mockSubscriptionIncomingNoticeHandler.handle(any(), any()))
			.thenReturn(mockSubscriptionUpcomingInvoiceResponse);
		when(mockAddonSubscriptionOrderHandler.handle(any(), any()))
			.thenReturn(mockAddonSubscriptionOrderResponse);
		when(mockAddonSubscriptionCancelHandler.handle(any(), any()))
			.thenReturn(mockAddonSubscriptionCancelResponse);
		when(mockUnknownEventHandler.handle(any(), any()))
			.thenReturn(mockUnknownEventResponse);
		when(mockUserAssignmentHandler.handle(any(), any()))
			.thenReturn(mockUserAssignmentResponse);
		when(mockUserUnassignmentHandler.handle(any(), any()))
			.thenReturn(mockUserUnassignmentResponse);
	}

	@Test
	public void testDispatchAndHandle_whenEventTypeNotSupported_theUnknownEventHandlerIsInvoked() throws Exception {
		//Given
		EventInfo testEvent = EventInfo.builder().type(EventType.USER_LINK).build();

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(testEvent, defaultEventContext());

		//Then
		assertThat(result).isEqualTo(mockUnknownEventResponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsSubscriptionOrder_thenInvokeAppropriateHandler() throws Exception {
		//Given
		EventInfo testEvent = someSubOrderEvent();

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(testEvent, defaultEventContext());

		//Then
		assertThat(result).isEqualTo(mockSubscriptionOrderResponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsSubscriptionCancel_thenInvokeAppropriateHandler() throws Exception {
		//Given
		EventInfo testEvent = someSubCancelEvent();

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(testEvent, defaultEventContext());

		//Then
		assertThat(result).isEqualTo(mockSubscriptionCancelResponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsSubscriptionChange_thenInvokeAppropriateHandler() throws Exception {
		//Given
		EventInfo testEvent = someSubChange();

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(testEvent, defaultEventContext());

		//Then
		assertThat(result).isEqualTo(mockSubscriptionChangeResponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsSubscriptionDeactivate_thenInvokeAppropriateHandler() throws Exception {
		//Given
		EventInfo testEvent = subscriptionNoticeOfType(DEACTIVATED);

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(testEvent, defaultEventContext());

		//Then
		assertThat(result).isEqualTo(mockSubscriptionDeactivatedResponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsSubscriptionReactivate_thenInvokeAppropriateHandler() throws Exception {
		//Given
		EventInfo testEvent = subscriptionNoticeOfType(REACTIVATED);

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(testEvent, defaultEventContext());

		//Then
		assertThat(result).isEqualTo(mockSubscriptionReactivatedResponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsSubscriptionClose_thenInvokeAppropriateHandler() throws Exception {
		//Given
		EventInfo testEvent = subscriptionNoticeOfType(CLOSED);

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(testEvent, defaultEventContext());

		//Then
		assertThat(result).isEqualTo(mockSubscriptionClosedResponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsSubscriptionUpcomingInvoice_thenInvokeAppropriateHandler() throws Exception {
		//Given
		EventInfo testEvent = subscriptionNoticeOfType(UPCOMING_INVOICE);

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(testEvent, defaultEventContext());

		//Then
		assertThat(result).isEqualTo(mockSubscriptionUpcomingInvoiceResponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsSubscriptionOrder_forAddon_thenInvokeAppropriateHandler() throws Exception {
		//Given
		EventInfo addonTestEvent = someSubOrderEvent();
		when(mockAddonDetector.eventIsRelatedToAddon(addonTestEvent)).thenReturn(true);

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(addonTestEvent, defaultEventContext());

		//Then
		assertThat(result).isEqualTo(mockAddonSubscriptionOrderResponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsSubscriptionCancel_forAddon_thenInvokeAppropriateHandler() throws Exception {
		//Given
		EventInfo addonTestEvent = someSubCancelEvent();
		when(mockAddonDetector.eventIsRelatedToAddon(addonTestEvent))
			.thenReturn(true);

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(addonTestEvent, defaultEventContext());

		//Then
		assertThat(result).isEqualTo(mockAddonSubscriptionCancelResponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsUserAssign_thenTheUserAssignHandlerIsInvoked() throws Exception {
		//Given
		EventInfo testEvent = someUserAssign();

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(testEvent, defaultEventContext());

		//Then
		assertThat(result).isEqualTo(mockUserAssignmentResponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventIsUserUnassign_thenTheUserUnassignmentHandlerIsInvoked() throws Exception {
		//Given
		EventInfo testEvent = someUserUnassign();

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(testEvent, defaultEventContext());

		//Then
		assertThat(result).isEqualTo(mockUserUnassignmentResponse);
	}

	@Test
	public void testDispatchAndHandle_whenTheEventShouldBeHandledAsync_sendItToAsyncHandler() throws Exception {
		//Given
		EventInfo testEvent = someSubOrderEvent();
		EventHandlingContext eventContext = defaultEventContext();
		APIResult asyncSuccess = success("ASYNC!!");
		when(mockEvents.eventShouldBeHandledAsync(testEvent)).thenReturn(true);
		when(mockAsyncEventHandler.handle(mockSubscriptionOrderHandler, testEvent, eventContext)).thenReturn(asyncSuccess);

		//When
		APIResult result = eventDispatcher.dispatchAndHandle(testEvent, eventContext);

		//Then
		assertThat(result).isEqualTo(asyncSuccess);
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

	private EventInfo someUserAssign() {
		return EventInfo.builder().type(USER_ASSIGNMENT).build();
	}

	private EventInfo someUserUnassign() {
		return EventInfo.builder().type(USER_UNASSIGNMENT).build();
	}
}
