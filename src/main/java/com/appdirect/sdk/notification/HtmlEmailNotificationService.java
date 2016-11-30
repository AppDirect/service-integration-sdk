package com.appdirect.sdk.notification;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Slf4j
@RequiredArgsConstructor
public class HtmlEmailNotificationService {
	private final String fromAddress;
	private final JavaMailSender sender;

	public void sendHtmlEmail(String messageSubject, String messageBody, String toAddress) throws SendNotificationFailedException {

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
