package com.appdirect.sdk.feature;

import static com.appdirect.sdk.appmarket.api.EventType.SUBSCRIPTION_ORDER;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.appdirect.sdk.ConnectorSdkConfiguration;
import com.appdirect.sdk.appmarket.AppmarketEventProcessor;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentials;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.api.APIResult;
import com.appdirect.sdk.appmarket.api.EventInfo;
import com.appdirect.sdk.appmarket.api.EventType;

@SpringBootApplication
@Import(ConnectorSdkConfiguration.class)
public class ConnectorWithOrderProcessor {
	@Bean
	public DeveloperSpecificAppmarketCredentialsSupplier credentialsSupplier() {
		return () -> new DeveloperSpecificAppmarketCredentials("isv-key", "isv-secret");
	}

	@Bean
	public AppmarketEventProcessor subscriptionOrderProcessor() {
		return new AppmarketEventProcessor() {
			@Override
			public boolean supports(EventType eventType) {
				return eventType == SUBSCRIPTION_ORDER;
			}

			@Override
			public APIResult process(EventInfo event, String baseAppmarketUrl) {
				return APIResult.success("SUB_ORDER has been processed, trust me.");
			}
		};
	}

}
