/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appdirect.sdk.web.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AsyncServletConfiguration {
	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

	@Bean
	public DispatcherServletPath dispatcherServletPath(DispatcherServlet dispatcherServlet) {
		DispatcherServletRegistrationBean registration = new DispatcherServletRegistrationBean(dispatcherServlet, "/");
		registration.setName("dispatcherServletRegistration");
		registration.setAsyncSupported(true);
		registration.setEnabled(true);
		registration.setLoadOnStartup(1);
		return registration;
	}

	/**
	 * Register dispatcherServlet programmatically
	 *
	 * @return ServletRegistrationBean
	 */
	@Bean
	public ServletRegistrationBean dispatcherServletRegistration() {

		ServletRegistrationBean registration = new ServletRegistrationBean(
			dispatcherServlet(), "/");

		registration
			.setName("dispatcherServletRegistration");
		registration.setAsyncSupported(true);
		registration.setEnabled(true);
		registration.setLoadOnStartup(1);

		return registration;
	}

	@Bean
	public AsyncTaskExecutor asyncTaskExecutor(@Value("${async.threadpool.coresize:16}") int corePoolSize, @Value("${async.threadpool.maxsize:64}") int maxPoolSize, @Value("${async.threadpool.capacity:1024}") int queueCapacity) {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(corePoolSize);
		taskExecutor.setMaxPoolSize(maxPoolSize);
		taskExecutor.setQueueCapacity(queueCapacity);
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		taskExecutor.afterPropertiesSet();
		return taskExecutor;
	}

	@Bean
	public WebMvcConfigurerAdapter webMvcConfigurerAdapter(AsyncTaskExecutor asyncTaskExecutor) {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
				configurer.setTaskExecutor(asyncTaskExecutor);
				super.configureAsyncSupport(configurer);
			}
		};
	}
}
