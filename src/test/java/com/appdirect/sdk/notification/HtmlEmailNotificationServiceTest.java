package com.appdirect.sdk.notification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
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
		String expectedRecipientAddress = "expectedRecepient@example.com";

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
		String expectedRecipientAddress = "expectedRecepient@example.com";

		when(mockEmailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
		doThrow(SendNotificationFailedException.class)
			.when(mockEmailSender).send(any(MimeMessage.class));

		//When
		assertThatThrownBy(() ->
			testedEmailService.sendHtmlEmail(expectedEmailSubject, expectedMessageBody, expectedRecipientAddress)
		).isInstanceOf(SendNotificationFailedException.class);
	}
}
