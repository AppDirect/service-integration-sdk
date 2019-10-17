package com.appdirect.sdk.meteredusage.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.appdirect.sdk.utils.ConstantUtils;
import retrofit2.Retrofit;

public class OAuth1RetrofitWrapperTest {
	private OAuth1RetrofitWrapper oAuth1RetrofitWrapper;

	@BeforeMethod
	public void setup() {
		oAuth1RetrofitWrapper = spy(new OAuth1RetrofitWrapper());
	}

	@Test
	public void testBaseUrl() {
		assertThat(oAuth1RetrofitWrapper.baseUrl(ConstantUtils.BASE_URL)).isInstanceOf(OAuth1RetrofitWrapper.class);
	}

	@Test
	public void testSign() {
		assertThat(oAuth1RetrofitWrapper.sign(ConstantUtils.CONSUMER_KEY, ConstantUtils.CONSUMER_SECRET)).isInstanceOf(OAuth1RetrofitWrapper.class);
	}

	@Test
	public void testBuild() {
		assertThat(oAuth1RetrofitWrapper.baseUrl(ConstantUtils.BASE_URL).build()).isExactlyInstanceOf(Retrofit.class);
	}
}
