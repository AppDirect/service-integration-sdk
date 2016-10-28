package com.appdirect.sdk.web;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.appdirect.sdk.appmarket.api.APIResult;

public class HealthControllerTest {

	private HealthController healthController = new HealthController();

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void healthPing() throws Exception {
		//When
		ResponseEntity<APIResult> apiResultResponseEntity = healthController.healthPing();

		//Then
		assertThat(apiResultResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
