package com.appdirect.sdk;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.appdirect.sdk.appmarket.events.AppmarketCommunicationConfiguration;
import com.appdirect.sdk.appmarket.events.DefaultEventHandlersForOptionalEvents;
import com.appdirect.sdk.appmarket.events.DeveloperExceptionHandler;
import com.appdirect.sdk.appmarket.events.EventHandlingConfiguration;
import com.appdirect.sdk.appmarket.migration.DefaultMigrationHandlers;
import com.appdirect.sdk.web.RestOperationsFactory;
import com.appdirect.sdk.web.config.JacksonConfiguration;
import com.appdirect.sdk.web.exception.AppmarketEventClientExceptionHandler;
import com.appdirect.sdk.web.oauth.SecurityConfiguration;

@Configuration
@Import({
	JacksonConfiguration.class,
	SecurityConfiguration.class,
	DefaultEventHandlersForOptionalEvents.class,
	EventHandlingConfiguration.class,
	AppmarketCommunicationConfiguration.class,
	DefaultMigrationHandlers.class
})
public class ConnectorSdkConfiguration {

	@Bean
	public AppmarketEventClientExceptionHandler appmarketEventConsumerExceptionHandler() {
		return new AppmarketEventClientExceptionHandler();
	}

	@Bean
	public RestOperationsFactory restOperationsFactory() {
		return new RestOperationsFactory(appmarketEventConsumerExceptionHandler());
	}

	@Bean
	public DeveloperExceptionHandler developerExceptionHandler() {
		return new DeveloperExceptionHandler();
	}

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
