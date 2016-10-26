package com.appdirect.sdk.feature;

import static com.appdirect.sdk.appmarket.api.type.EventType.SUBSCRIPTION_ORDER;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.appdirect.sdk.ConnectorSdkConfiguration;
import com.appdirect.sdk.appmarket.AppmarketEventProcessor;
import com.appdirect.sdk.appmarket.IsvSpecificAppmarketCredentials;
import com.appdirect.sdk.appmarket.IsvSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.appmarket.api.type.EventType;
import com.appdirect.sdk.appmarket.api.vo.APIResult;
import com.appdirect.sdk.appmarket.api.vo.EventInfo;

@SpringBootApplication
@Import(ConnectorSdkConfiguration.class)
public class ConnectorWithOrderProcessor {
	@Bean
	public IsvSpecificAppmarketCredentialsSupplier credentialsSupplier() {
		return () -> new IsvSpecificAppmarketCredentials("isv-key", "isv-secret");
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
