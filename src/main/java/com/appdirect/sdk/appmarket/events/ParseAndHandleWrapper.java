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

import com.appdirect.sdk.appmarket.AppmarketEventHandler;

/**
 * SDK internal - Convenience class that parses an event into a rich event and sends it to its event handler
 *
 * @param <T> the type of the rich event the parser and handler support
 */
class ParseAndHandleWrapper<T> implements SDKEventHandler {
	private final EventParser<T> parser;
	private final AppmarketEventHandler<T> eventHandler;

	ParseAndHandleWrapper(EventParser<T> parser, AppmarketEventHandler<T> eventHandler) {
		this.parser = parser;
		this.eventHandler = eventHandler;
	}

	@Override
	public APIResult handle(EventInfo event, EventHandlingContext eventContext) {
		T parsedEvent = parser.parse(event, eventContext);
		return eventHandler.handle(parsedEvent);
	}
}
