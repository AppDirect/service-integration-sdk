package com.appdirect.sdk.appmarket.api.vo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class APIResultTest {

	@Test
	public void constructorWithError_setsSuccessToFalse() throws Exception {
		APIResult unsuccessfulResult = new APIResult(ErrorCode.CONFIGURATION_ERROR, "some-message");

		assertThat(unsuccessfulResult.isSuccess()).isFalse();
		assertThat(unsuccessfulResult.getErrorCode()).isEqualTo(ErrorCode.CONFIGURATION_ERROR);
		assertThat(unsuccessfulResult.getMessage()).isEqualTo("some-message");
	}
}
