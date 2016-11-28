package com.appdirect.sdk.appmarket.events;

import static com.appdirect.sdk.appmarket.events.APIResult.success;
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
		SubscriptionOrder theRichEvent = mock(SubscriptionOrder.class);
		when(parser.parse("oauth-key", theEvent)).thenReturn(theRichEvent);
		when(handler.handle(theRichEvent)).thenReturn(success("All is good"));

		APIResult result = wrapper.handle("oauth-key", theEvent);

		assertThat(result.getMessage()).isEqualTo("All is good");
	}
}
