package com.appdirect.sdk.web.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class AsyncServletConfiguration {
	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
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
			.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
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
