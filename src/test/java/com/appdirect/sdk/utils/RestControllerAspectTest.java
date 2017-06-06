package com.appdirect.sdk.utils;

import static com.appdirect.sdk.utils.RestControllerAspect.MDC_REQUEST_ID_KEY;
import static com.appdirect.sdk.utils.RestControllerAspect.REQUEST_ID_HEADER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.appdirect.sdk.feature.sample_connector.minimal.MinimalConnector;
import com.appdirect.sdk.support.DummyRestController;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MinimalConnector.class, webEnvironment = RANDOM_PORT)
public class RestControllerAspectTest {

	@Autowired
	private DummyRestController dummyRestController;

	@Test
	public void shouldIncludeMdcWhenCallingRestControllerMethodWithReuest() throws Exception {

		//Given
		String uuid = "some_uuid";
		HttpServletRequest request = aRequestContainingHeader(REQUEST_ID_HEADER, uuid);

		//When
		dummyRestController.doNothing(request);

		//Then
		assertThat(MDC.get(MDC_REQUEST_ID_KEY)).isEqualTo(uuid);
	}

	@Test
	public void shouldNotIncludeMdcWhenCallingRestControllerMethodWithReuest() throws Exception {

		//When
		dummyRestController.doNothing();

		//Then
		assertThat(MDC.get(MDC_REQUEST_ID_KEY)).isNull();
	}

	private HttpServletRequest aRequestContainingHeader(String key, String value) {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(key, value);
		return request;
	}
}
