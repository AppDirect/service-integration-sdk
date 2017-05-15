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

/**
 * SDK internal - parses an EventInfo and returns a rich event of type T
 *
 * @param <T> the type of the rich event returned by this parser
 */
@FunctionalInterface
interface EventParser<T> {
	/**
	 * Parses a raw {@link EventInfo} instance received from the AppMarket into the corresponding
	 * rich event type (that is visible to clients of the SDK)
	 *
	 * @param eventInfo    representation of the raw event fetched from the AppMarket
	 * @param eventContext contextual information about the event
	 * @return The rich (client visible) event corresponding to the {@link EventInfo} input parameter
	 */
	T parse(EventInfo eventInfo, EventHandlingContext eventContext);
}
