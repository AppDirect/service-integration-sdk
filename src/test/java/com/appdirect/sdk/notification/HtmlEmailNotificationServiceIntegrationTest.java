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

import static com.icegreen.greenmail.util.GreenMailUtil.getBody;
import static com.icegreen.greenmail.util.GreenMailUtil.getHeaders;
import static com.icegreen.greenmail.util.ServerSetup.PROTOCOL_SMTP;
import static java.lang.System.lineSeparator;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.springframework.util.SocketUtils.findAvailableTcpPort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import lombok.extern.slf4j.Slf4j;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

@Slf4j
public class HtmlEmailNotificationServiceIntegrationTest {
	private GreenMail greenMail;

	private HtmlEmailNotificationService htmlEmailNotificationService;

	@Before
	public void setUp() throws Exception {
		String mailServerHost = "127.0.0.1";
		int mailServerPort = findAvailableTcpPort();

		log.warn("Port selected: {}", mailServerPort);
		greenMail = new GreenMail(
			new ServerSetup(mailServerPort, mailServerHost, PROTOCOL_SMTP)
		);

		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(mailServerHost);
		sender.setPort(mailServerPort);

		htmlEmailNotificationService = new HtmlEmailNotificationService("from@example.com", sender);
		greenMail.start();
	}

	@After
	public void tearDown() throws Exception {
		greenMail.stop();
	}

	@Test
	public void testSendEmailNotification_whenEmailNotificationSent_theTargetSMTPServerReceivesIt() throws Exception {

		//Given
		String expectedEmailBody = "testBody";
		String expectedEmailSubject = "testSubject";
		String expectedRecipient = "testReceiver@example.com";

		//When
		htmlEmailNotificationService.sendHtmlEmail(expectedEmailSubject, expectedEmailBody, expectedRecipient);
		greenMail.waitForIncomingEmail(1);
		MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];

		//Then
		assertThat(getBody(receivedMessage)).isEqualTo(expectedEmailBody);
		assertThat(headers(receivedMessage)).contains(entry("Subject", expectedEmailSubject));
	}

	private Map<String, String> headers(MimeMessage receivedMessage) {
		String headersAsString = getHeaders(receivedMessage);
		List<String> headers = asList(headersAsString.split(lineSeparator()));

		return headers.stream().collect(
			HashMap::new,
			(hashMap, keyValue) -> {
				String[] split = keyValue.split(":");
				hashMap.put(split[0], split[1].trim());
			},
			(map1, map2) -> map2.putAll(map1)
		);
	}
}
