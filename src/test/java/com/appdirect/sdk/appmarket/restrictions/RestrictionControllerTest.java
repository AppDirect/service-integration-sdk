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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.matches;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.appdirect.sdk.appmarket.restrictions.context.RestrictionInfo;

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
		RestrictionInfo restrictionInfo = mock(RestrictionInfo.class);
		RestrictionResponse restrictionResponse = mock(RestrictionResponse.class);
		when(restrictionHandler.getRestrictions(any(RestrictionInfo.class), matches("appdirect"))).thenReturn(restrictionResponse);
		restrictionController.getRestrictions(restrictionInfo, "appdirect").call();
		verify(restrictionHandler, times(1)).getRestrictions(any(RestrictionInfo.class), anyString());
	}
}
