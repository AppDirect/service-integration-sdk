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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.appdirect.sdk.appmarket.AppmarketEventHandler;

@RunWith(MockitoJUnitRunner.class)
public class ParseAndHandleWrapperTest {
	@Mock
	private EventParser<SubscriptionOrder> parser;
	@Mock
	private AppmarketEventHandler<SubscriptionOrder> handler;
	private ParseAndHandleWrapper<SubscriptionOrder> wrapper;

	@Before
	public void setUp() throws Exception {
		wrapper = new ParseAndHandleWrapper<>(parser, handler);
	}

	@Test
	public void testHandle_parsesEvent_sendsItToTheHandler_thenReturnResults() throws Exception {
		EventInfo theEvent = EventInfo.builder().build();
		EventHandlingContext eventContext = defaultEventContext();
		SubscriptionOrder theRichEvent = mock(SubscriptionOrder.class);
		when(parser.parse(theEvent, eventContext)).thenReturn(theRichEvent);
		when(handler.handle(theRichEvent)).thenReturn(success("All is good"));

		APIResult result = wrapper.handle(theEvent, eventContext);

		assertThat(result.getMessage()).isEqualTo("All is good");
	}
}
