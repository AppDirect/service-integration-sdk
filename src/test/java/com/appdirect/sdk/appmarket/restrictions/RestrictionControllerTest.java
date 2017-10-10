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
 *
 */

package com.appdirect.sdk.appmarket.restrictions;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.appdirect.sdk.appmarket.restrictions.context.RestrictionContext;

@RunWith(MockitoJUnitRunner.class)
public class RestrictionControllerTest {
	@Mock
	private RestrictionHandler restrictionHandler;
	private RestrictionController restrictionController;

	@Before
	public void setup() throws Exception {
		restrictionController = new RestrictionController(restrictionHandler);
	}

	@Test
	public void testGetRestrictions() throws Exception {
		RestrictionContext restrictionContext = mock(RestrictionContext.class);
		RestrictionResponse restrictionResponse = mock(RestrictionResponse.class);
		when(restrictionHandler.getRestrictions(Matchers.any(RestrictionContext.class), Matchers.matches("appdirect"))).thenReturn(restrictionResponse);
		restrictionController.getRestrictions(restrictionContext, "appdirect").call();
		verify(restrictionHandler, times(1)).getRestrictions(Matchers.any(RestrictionContext.class), Matchers.anyString());
	}
}
