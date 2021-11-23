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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.assertj.core.api.exception.RuntimeIOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;

@RunWith(MockitoJUnitRunner.class)
public class HtmlEmailNotificationServiceTest {

	private HtmlEmailNotificationService testedEmailService;

	private String expectedSenderEmail = "mockSender@example.com";
	@Mock
	private JavaMailSender mockEmailSender;

	@Before
	public void setUp() throws Exception {
		testedEmailService = new HtmlEmailNotificationService(expectedSenderEmail, mockEmailSender);
	}

	@Test
	public void sendHtmlEmail_whenNotificationSent_aCorrespondingEmailSent() throws Exception {
		//Given
		String expectedEmailSubject = "expectedEmailSubject";
		String expectedMessageBody = "expectedMessageBody";
		String expectedRecipientAddress = "expectedRecipient@example.com";

		when(mockEmailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
		ArgumentCaptor<MimeMessage> argumentCaptor = ArgumentCaptor.forClass(MimeMessage.class);

		//When
		testedEmailService.sendHtmlEmail(expectedEmailSubject, expectedMessageBody, expectedRecipientAddress);

		//Then
		verify(mockEmailSender)
			.send(argumentCaptor.capture());
		MimeMessage messageSent = argumentCaptor.getValue();

		assertThat((String) messageSent.getContent())
			.isEqualTo(expectedMessageBody);
		assertThat(messageSent.getHeader("Subject")[0])
			.isEqualTo(expectedEmailSubject);
		assertThat(messageSent.getHeader("To")[0])
			.isEqualTo(expectedRecipientAddress);
		assertThat(messageSent.getHeader("From")[0])
			.isEqualTo(expectedSenderEmail);
	}

	@Test
	public void sendHtmlEmail_whenMessageExceptionThrownByJavaApi_itIsWrappedInApplicationLevelException() throws Exception {
		//Given
		String expectedEmailSubject = "expectedEmailSubject";
		String expectedMessageBody = "expectedMessageBody";
		String expectedRecipientAddress = "expectedRecipient@example.com";

		when(mockEmailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
		doThrow(new RuntimeIOException("test"))
			.when(mockEmailSender).send(any(MimeMessage.class));

		//When
		assertThatThrownBy(() ->
			testedEmailService.sendHtmlEmail(expectedEmailSubject, expectedMessageBody, expectedRecipientAddress)
		)
			.isInstanceOf(SendNotificationFailedException.class)
			.hasMessage(
				"Failed sending email notification with from=mockSender@example.com, to=expectedRecipient@example.com, notification=expectedMessageBody"
			);
	}
}
