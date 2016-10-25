package com.appdirect.sdk.isv.api.model.vo;

import static com.appdirect.sdk.isv.api.model.vo.ErrorCode.CONFIGURATION_ERROR;
import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

public class APIResultTest {

	@Test
	public void constructorWithError_setsSuccessToFalse() throws Exception {
		APIResult unsuccessfulResult = new APIResult(CONFIGURATION_ERROR, "some-message");

		assertThat(unsuccessfulResult.isSuccess()).isFalse();
		assertThat(unsuccessfulResult.getErrorCode()).isEqualTo(CONFIGURATION_ERROR);
		assertThat(unsuccessfulResult.getMessage()).isEqualTo("some-message");
	}
}
