package com.appdirect.sdk.appmarket.usersync;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.web.RestOperationsFactory;
import com.appdirect.sdk.web.exception.UserSyncApiExceptionHandler;

@Configuration
public class UserSyncConfiguration {

	@Bean
	UserSyncApiExceptionHandler userSyncApiExceptionHandler() {
		return new UserSyncApiExceptionHandler();
	}

	@Bean
	public RestOperationsFactory userSyncRestOperationsFactory() {
		return new RestOperationsFactory(userSyncApiExceptionHandler());
	}

	@Bean
	UserSyncApiClient userSyncApiClient() {
		return new UserSyncApiClient(userSyncRestOperationsFactory());
	}
}
