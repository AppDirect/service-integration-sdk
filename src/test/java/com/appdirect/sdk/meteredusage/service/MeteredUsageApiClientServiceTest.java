package com.appdirect.sdk.meteredusage.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.appdirect.sdk.appmarket.Credentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.appmarket.events.ErrorCode;
import com.appdirect.sdk.meteredusage.MeteredUsageApi;
import com.appdirect.sdk.meteredusage.RetrofitCallStub;
import com.appdirect.sdk.meteredusage.config.OAuth1RetrofitWrapper;
import com.appdirect.sdk.meteredusage.model.MeteredUsageItem;
import com.appdirect.sdk.meteredusage.model.MeteredUsageRequest;
import com.appdirect.sdk.meteredusage.model.MeteredUsageResponse;
import com.appdirect.sdk.meteredusage.mother.MeteredUsageItemMother;
import com.appdirect.sdk.utils.ConstantUtils;
import com.google.inject.internal.Lists;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Response;

public class MeteredUsageApiClientServiceTest {

	@Mock
	private DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier;

	private MeteredUsageApiClientServiceImpl meteredUsageApiClientService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		OAuth1RetrofitWrapper oAuth1RetrofitWrapper = new OAuth1RetrofitWrapper();

		meteredUsageApiClientService = spy(new MeteredUsageApiClientServiceImpl(credentialsSupplier, oAuth1RetrofitWrapper));

		when(credentialsSupplier.getConsumerCredentials(ConstantUtils.CONSUMER_KEY)).thenReturn(new Credentials(ConstantUtils.CONSUMER_KEY, ConstantUtils.CONSUMER_SECRET));
	}

	@Test
	public void testBillUsage_noErrors() {

		MeteredUsageResponse requestAccepted = new MeteredUsageResponse(ConstantUtils.REQUEST_ID, ConstantUtils.IDEMPOTENCY_KEY);

		MeteredUsageItem meteredUsageItem = MeteredUsageItemMother.basic().build();

		MeteredUsageApi meteredUsageApi = mock(MeteredUsageApi.class);
		Response<MeteredUsageResponse> response = buildResponse(requestAccepted);
		Call<MeteredUsageResponse> call = new RetrofitCallStub(response).getCall();
		List<MeteredUsageItem> items = Lists.newArrayList(meteredUsageItem);

		doReturn(call).when(meteredUsageApi).meteredUsageCall(any());
		doReturn(meteredUsageApi).when(meteredUsageApiClientService).createMeteredUsageApi(ConstantUtils.BASE_URL, ConstantUtils.CONSUMER_KEY, ConstantUtils.CONSUMER_SECRET);

		APIResult result = meteredUsageApiClientService.reportUsage(ConstantUtils.BASE_URL, ConstantUtils.IDEMPOTENCY_KEY, items, ConstantUtils.BILLABLE, ConstantUtils.CONSUMER_KEY, ConstantUtils.CONSUMER_SECRET, ConstantUtils.EMPTY_SOURCE_TYPE);

		assertThat(result.isSuccess()).isTrue();
	}

	@Test
	public void testSourceTypeUsage_noErrors() {

		MeteredUsageResponse requestAccepted = new MeteredUsageResponse(ConstantUtils.REQUEST_ID, ConstantUtils.IDEMPOTENCY_KEY);

		MeteredUsageItem meteredUsageItem = MeteredUsageItemMother.basic().build();

		MeteredUsageApi meteredUsageApi = mock(MeteredUsageApi.class);
		Response<MeteredUsageResponse> response = buildResponse(requestAccepted);
		Call<MeteredUsageResponse> call = new RetrofitCallStub(response).getCall();
		List<MeteredUsageItem> items = Lists.newArrayList(meteredUsageItem);

		doReturn(call).when(meteredUsageApi).meteredUsageCall(any());
		doReturn(meteredUsageApi).when(meteredUsageApiClientService).createMeteredUsageApi(ConstantUtils.BASE_URL, ConstantUtils.CONSUMER_KEY, ConstantUtils.CONSUMER_SECRET);

		APIResult result = meteredUsageApiClientService.reportUsage(ConstantUtils.BASE_URL, ConstantUtils.IDEMPOTENCY_KEY, items, ConstantUtils.BILLABLE, ConstantUtils.CONSUMER_KEY, ConstantUtils.CONSUMER_SECRET, ConstantUtils.SOURCE_TYPE);

		assertThat(result.isSuccess()).isTrue();
	}

	@Test
	public void testBillUsage_unsuccessfulCall() {

		MeteredUsageItem meteredUsageItem = MeteredUsageItemMother.basic().build();

		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

		MeteredUsageApi meteredUsageApi = mock(MeteredUsageApi.class);
		Response<MeteredUsageResponse> response = buildResponse(httpStatus, ErrorCode.UNKNOWN_ERROR.toString());
		Call<MeteredUsageResponse> call = new RetrofitCallStub(response).getCall();
		List<MeteredUsageItem> items = Lists.newArrayList(meteredUsageItem);

		doReturn(call).when(meteredUsageApi).meteredUsageCall(any());
		doReturn(meteredUsageApi).when(meteredUsageApiClientService).createMeteredUsageApi(ConstantUtils.BASE_URL, ConstantUtils.CONSUMER_KEY, ConstantUtils.CONSUMER_SECRET);

		APIResult result = meteredUsageApiClientService.reportUsage(ConstantUtils.BASE_URL, ConstantUtils.IDEMPOTENCY_KEY, items, ConstantUtils.BILLABLE, ConstantUtils.CONSUMER_KEY, ConstantUtils.CONSUMER_SECRET,  ConstantUtils.EMPTY_SOURCE_TYPE);

		assertThat(result.isSuccess()).isFalse();
	}

	@Test
	public void testBillUsageNullSecret_noErrors() {

		MeteredUsageResponse requestAccepted = new MeteredUsageResponse(ConstantUtils.REQUEST_ID, ConstantUtils.IDEMPOTENCY_KEY);

		MeteredUsageItem meteredUsageItem = MeteredUsageItemMother.basic().build();

		MeteredUsageApi meteredUsageApi = mock(MeteredUsageApi.class);
		Response<MeteredUsageResponse> response = buildResponse(requestAccepted);
		Call<MeteredUsageResponse> call = new RetrofitCallStub(response).getCall();
		List<MeteredUsageItem> items = Lists.newArrayList(meteredUsageItem);

		doReturn(call).when(meteredUsageApi).meteredUsageCall(any());
		doReturn(meteredUsageApi).when(meteredUsageApiClientService).createMeteredUsageApi(ConstantUtils.BASE_URL, ConstantUtils.CONSUMER_KEY, null);

		APIResult result = meteredUsageApiClientService.reportUsage(ConstantUtils.BASE_URL, ConstantUtils.IDEMPOTENCY_KEY, items, ConstantUtils.BILLABLE, ConstantUtils.CONSUMER_KEY, null, ConstantUtils.EMPTY_SOURCE_TYPE);

		assertThat(result.isSuccess()).isTrue();
	}

	@Test
	public void testBillUsageNullSecret_unsuccessfulCall() {

		MeteredUsageItem meteredUsageItem = MeteredUsageItemMother.basic().build();

		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

		MeteredUsageApi meteredUsageApi = mock(MeteredUsageApi.class);
		Response<MeteredUsageResponse> response = buildResponse(httpStatus, ErrorCode.UNKNOWN_ERROR.toString());
		Call<MeteredUsageResponse> call = new RetrofitCallStub(response).getCall();
		List<MeteredUsageItem> items = Lists.newArrayList(meteredUsageItem);

		doReturn(call).when(meteredUsageApi).meteredUsageCall(any());
		doReturn(meteredUsageApi).when(meteredUsageApiClientService).createMeteredUsageApi(ConstantUtils.BASE_URL, ConstantUtils.CONSUMER_KEY, null);

		APIResult result = meteredUsageApiClientService.reportUsage(ConstantUtils.BASE_URL, ConstantUtils.IDEMPOTENCY_KEY, items, ConstantUtils.BILLABLE, ConstantUtils.CONSUMER_KEY, null, ConstantUtils.EMPTY_SOURCE_TYPE);

		assertThat(result.isSuccess()).isFalse();
	}

	@Test
	public void testSendSubscriptionIdInMeteredUsageItem_getsSerialized() {

		Call<MeteredUsageResponse> call = buildCall(MeteredUsageItemMother.withSubscriptionId().build());
		String body = getCallBodyAsAString(call);

		assertThat(body).isNotNull();
		assertThat(body).contains(ConstantUtils.SUBSCRIPTION_ID);
	}

	@Test
	public void testDONTSendSubscriptionIdInMeteredUsageItem_doesntGetSerialized() {

		Call<MeteredUsageResponse> call = buildCall(MeteredUsageItemMother.basic().build());
		String body = getCallBodyAsAString(call);

		assertThat(body).isNotNull();
		assertThat(body).doesNotContain(ConstantUtils.SUBSCRIPTION_ID);
	}

	private Call<MeteredUsageResponse> buildCall(MeteredUsageItem meteredUsageItem) {
		MeteredUsageApi meteredUsageApi = meteredUsageApiClientService.createMeteredUsageApi(ConstantUtils.BASE_URL, ConstantUtils.CONSUMER_KEY, ConstantUtils.CONSUMER_SECRET);
		MeteredUsageRequest meteredUsageRequest = MeteredUsageRequest.builder()
			.idempotencyKey(ConstantUtils.IDEMPOTENCY_KEY)
			.billable(ConstantUtils.BILLABLE)
			.usages(Collections.singletonList(meteredUsageItem))
			.sourceType(ConstantUtils.SOURCE_TYPE)
			.build();
		return meteredUsageApi.meteredUsageCall(meteredUsageRequest);
	}

	private String getCallBodyAsAString(Call<MeteredUsageResponse> call) {
		String body = null;
		try {
			Buffer buffer = new Buffer();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			call.request().body().writeTo(buffer);
			buffer.copyTo(stream);
			body = new String(stream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}

	private Response<MeteredUsageResponse> buildResponse(MeteredUsageResponse meteredUsageResponse) {
		return Response.success(meteredUsageResponse);
	}

	private Response<MeteredUsageResponse> buildResponse(HttpStatus status, String body) {
		ResponseBody responseBody = ResponseBody.create(MediaType.parse(body), body);
		return Response.error(status.value(), responseBody);
	}
}
