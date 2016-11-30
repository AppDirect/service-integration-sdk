package com.appdirect.sdk.notification;

import static com.icegreen.greenmail.util.GreenMailUtil.getBody;
import static com.icegreen.greenmail.util.GreenMailUtil.getHeaders;
import static com.icegreen.greenmail.util.ServerSetup.PROTOCOL_SMTP;
import static java.lang.System.lineSeparator;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

public class HtmlEmailNotificationServiceIntegrationTest {

	private static final int TEST_SMTP_SERVER_PORT = 3025;
	private static final String TEST_SMTP_SERVER_BIND_ADDRESS = "127.0.0.1";

	private GreenMail greenMail = new GreenMail(
		new ServerSetup(TEST_SMTP_SERVER_PORT, TEST_SMTP_SERVER_BIND_ADDRESS, PROTOCOL_SMTP)
	);

	private HtmlEmailNotificationService htmlEmailNotificationService;

	@Before
	public void setUp() throws Exception {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(TEST_SMTP_SERVER_BIND_ADDRESS);
		sender.setPort(TEST_SMTP_SERVER_PORT);

		htmlEmailNotificationService = new HtmlEmailNotificationService("from@example.com", sender);
		greenMail.start();
	}

	@After
	public void tearDown() throws Exception {
		greenMail.stop();
	}

	@Test
	public void testSendEmailNotification_whenEmailNotificationSent_theTargetSmtpServerReceivesIt() throws Exception {

		//Given
		String expectedEmailBody = "testBody";
		String expectedEmailSubject = "testSubject";
		String expectedRecepient = "testReceiver@example.com";

		//When
		htmlEmailNotificationService.sendHtmlEmail(expectedEmailSubject, expectedEmailBody, expectedRecepient);
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
