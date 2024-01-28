package com.project.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
	private final JavaMailSender mailSender;

	public EmailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public String sendMail(String toEmail, String body, String subject) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setText(body);
		message.setTo(toEmail);
		message.setFrom("jesse.band@gmail.com");
		message.setSubject(subject);

		try {
			mailSender.send(message);
			return "Email enviado com sucesso!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao enviar email.";
		}
	}
}
