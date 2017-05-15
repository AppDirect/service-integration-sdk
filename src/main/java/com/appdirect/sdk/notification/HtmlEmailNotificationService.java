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

package com.appdirect.sdk.notification;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * A service that can be used by a connector in order to send emails.
 */
@Slf4j
@RequiredArgsConstructor
public class HtmlEmailNotificationService {
	private final String fromAddress;
	private final JavaMailSender sender;

	public void sendHtmlEmail(String messageSubject, String messageBody, String toAddress) {

		try {
			MimeMessageHelper helper = new MimeMessageHelper(sender.createMimeMessage());
			helper.setFrom(new InternetAddress(fromAddress));
			helper.setTo(new InternetAddress(toAddress));
			helper.setSubject(messageSubject);
			helper.setText(messageBody, true);
			sender.send(helper.getMimeMessage());
		} catch (MessagingException e) {
			String errorMessage = String.format(
				"Failed sending email notification with from=%s, to=%s, notification=%s",
				fromAddress,
				toAddress,
				messageBody
			);
			log.error(errorMessage, e);
			throw new SendNotificationFailedException(errorMessage, e);
		}
	}
}
