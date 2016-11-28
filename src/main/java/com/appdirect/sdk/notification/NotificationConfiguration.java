package com.appdirect.sdk.notification;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class NotificationConfiguration {

	@Bean
	public HtmlEmailNotificationService notificationService(JavaMailSender javaMailSender) {
		return new HtmlEmailNotificationService(javaMailSender);
	}
}
