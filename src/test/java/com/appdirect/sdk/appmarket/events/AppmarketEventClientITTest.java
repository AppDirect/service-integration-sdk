package com.appdirect.sdk.appmarket.events;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.lang.String.format;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.nio.charset.Charset;
import java.util.UUID;

import org.apache.http.client.utils.URIBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.appdirect.sdk.feature.sample_connector.full.FullConnector;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import wiremock.com.google.common.io.Resources;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FullConnector.class, webEnvironment = RANDOM_PORT)
public class AppmarketEventClientITTest {

	@Rule
	public WireMockRule mockHttpServer = new WireMockRule(wireMockConfig().dynamicPort());

	private String mockAppMarketUrl;

	@Autowired
	private AppmarketEventClient appmarketEventClient;

	@Before
	public void setUp() throws Exception {
		mockAppMarketUrl = new URIBuilder().setScheme("http").setHost("localhost").setPort(mockHttpServer.port()).build().toString();
	}

	@Test
	public void whenResolvingEvent_thenACorrectJsonPayloadShouldBeSentToTheAppMarket() throws Exception {
		//Given
		String testEventToken = UUID.randomUUID().toString();
		APIResult testApiResult = APIResult.failure(ErrorCode.UNKNOWN_ERROR, "kaboom");
		String testUserKey = "aKey";
		final String eventResolutionEntpointUrl = format("/api/integration/v1/events/%s/result", testEventToken);

		final String expectedCallbackPayload = Resources.toString(Resources.getResource("resolutionCallbacks/unknown_error.json"), Charset.forName("UTF-8"));

		mockHttpServer
				.givenThat(
						post(
								urlEqualTo(eventResolutionEntpointUrl)
						).willReturn(
								aResponse().withStatus(200)
						)
				);

		//When
		appmarketEventClient.resolve(mockAppMarketUrl, testEventToken, testApiResult, testUserKey);

		//Then
		mockHttpServer
				.verify(
						postRequestedFor(
								urlEqualTo(eventResolutionEntpointUrl)
						).withRequestBody(
								equalToJson(expectedCallbackPayload)
						).withHeader(
								"Authorization", containing("OAuth oauth_consumer_key=")
						).withHeader(
								"Content-Type", equalTo("application/json")
						)
				);
	}
}
