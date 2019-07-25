package com.appdirect.sdk.meteredUsage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.meteredUsage.exception.APIErrorCode;
import com.appdirect.sdk.meteredUsage.model.MeteredUsageItem;
import com.appdirect.sdk.meteredUsage.model.MeteredUsageRequestAccepted;
import com.appdirect.sdk.meteredUsage.mother.MeteredUsageItemMother;
import com.appdirect.sdk.meteredUsage.service.MeteredUsageApiClientServiceImpl;
import com.appdirect.sdk.utils.ConstantUtils;
import com.google.inject.internal.Lists;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MeteredUsageApiClientServiceTest {

	@Mock
	private DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;

	private MeteredUsageApiClientServiceImpl meteredUsageApiClientService;

	private Retrofit.Builder meteredUsageRetrofitBuilder = new Retrofit.Builder()
			.baseUrl(ConstantUtils.BASE_URL)
			.addConverterFactory(JacksonConverterFactory.create());

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		meteredUsageApiClientService = spy(new MeteredUsageApiClientServiceImpl(meteredUsageRetrofitBuilder, credentialsSupplier));

		when(credentialsSupplier.getConsumerCredentials(ConstantUtils.CONSUMER_KEY)).thenReturn(new Credentials(ConstantUtils.CONSUMER_KEY, ConstantUtils.CONSUMER_SECRET));
	}

	@Test
	public void testBillUsage_noErrors() {

		MeteredUsageRequestAccepted requestAccepted = new MeteredUsageRequestAccepted(ConstantUtils.REQUEST_ID, ConstantUtils.IDEMPOTENCY_KEY);

		MeteredUsageItem meteredUsageItem = MeteredUsageItemMother.basic().build();

		MeteredUsageApi meteredUsageApi = mock(MeteredUsageApi.class);
		Response<MeteredUsageRequestAccepted> response = buildResponse(requestAccepted);
		Call<MeteredUsageRequestAccepted> call = new RetrofitCallStub(response).getCall();
		List<MeteredUsageItem> items = Lists.newArrayList(meteredUsageItem);

		doReturn(call).when(meteredUsageApi).meteredUsageCall(any());
		doReturn(meteredUsageApi).when(meteredUsageApiClientService).createMeteredUsageApi(any(), any());

		APIResult result = meteredUsageApiClientService.reportUsage(ConstantUtils.BASE_URL, ConstantUtils.CONSUMER_KEY, ConstantUtils.IDEMPOTENCY_KEY, items);

		assertThat(result.isSuccess()).isTrue();
	}

	@Test
	public void testBillUsage_unsuccessfulCall() {

		MeteredUsageItem meteredUsageItem = MeteredUsageItemMother.basic().build();

		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

		MeteredUsageApi meteredUsageApi = mock(MeteredUsageApi.class);
		Response<MeteredUsageRequestAccepted> response = buildResponse(httpStatus, APIErrorCode.UNKNOWN.getDescription());
		Call<MeteredUsageRequestAccepted> call = new RetrofitCallStub(response).getCall();
		List<MeteredUsageItem> items = Lists.newArrayList(meteredUsageItem);

		doReturn(call).when(meteredUsageApi).meteredUsageCall(any());
		doReturn(meteredUsageApi).when(meteredUsageApiClientService).createMeteredUsageApi(any(), any());

		APIResult result = meteredUsageApiClientService.reportUsage(ConstantUtils.BASE_URL, ConstantUtils.CONSUMER_KEY, ConstantUtils.IDEMPOTENCY_KEY, items);

		assertThat(result.isSuccess()).isFalse();
	}

	private Response<MeteredUsageRequestAccepted> buildResponse(MeteredUsageRequestAccepted meteredUsageRequestAccepted) {
		return Response.success(meteredUsageRequestAccepted);
	}

	private Response<MeteredUsageRequestAccepted> buildResponse(HttpStatus status, String body) {
		ResponseBody responseBody = ResponseBody.create(MediaType.parse(body), body);
		return Response.error(status.value(), responseBody);
	}
}
